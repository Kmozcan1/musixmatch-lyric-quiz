package com.kmozcan1.lyricquizapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kmozcan1.lyricquizapp.domain.interactor.CountdownUseCase
import com.kmozcan1.lyricquizapp.domain.interactor.QuizUseCase
import com.kmozcan1.lyricquizapp.domain.interactor.InsertScoreUseCase
import com.kmozcan1.lyricquizapp.domain.model.Question
import com.kmozcan1.lyricquizapp.domain.model.QuizResponse
import com.kmozcan1.lyricquizapp.presentation.viewstate.QuizViewState
import kotlin.properties.Delegates

class QuizViewModel @ViewModelInject constructor(
    private val quizUseCase: QuizUseCase,
    private val countdownUseCase: CountdownUseCase,
    private val insertScoreUseCase: InsertScoreUseCase
) : BaseViewModel<QuizViewState>() {

    // LiveData to observe the timer
    val timerLiveData: LiveData<String>
        get() = _timerLiveData
    private val _timerLiveData = MutableLiveData<String>()
    private fun setTimerLiveData(value: String) {
        _timerLiveData.postValue(value)
    }

    private lateinit var questionList: List<Question>

    private var timeLimit by Delegates.notNull<Long>()

    private var questionIndex = 0


    // Creates the quest and starts asking questions onSuccess
    fun createQuiz() {
        setViewState(QuizViewState.loading())
        quizUseCase.execute(
            params = QuizUseCase.Params(),
            onSubscribe = {
                quizUseCase.generateQuiz()
            },
            onNext = { quizResponse ->
                when (quizResponse.responseType) {
                    QuizResponse.ResponseType.QUIZ_GENERATED -> {
                        timeLimit = quizResponse.timeLimit!!
                        setViewState(QuizViewState.quizGenerated())
                    }
                    QuizResponse.ResponseType.QUESTION -> {
                        setViewState(QuizViewState.question(quizResponse.question!!))
                        startTimer()
                    }
                    QuizResponse.ResponseType.ANSWER -> {
                        setViewState(QuizViewState.answer(quizResponse.answer!!))
                    }
                    QuizResponse.ResponseType.FINALIZE_QUIZ -> {
                        finalizeQuest(quizResponse.finalScore!!)
                    }
                }
            },
            onError = {
                onError(it)
            }
        )
    }

    fun startQuiz() {
        quizUseCase.startQuiz()
    }

    fun stopTimer() {
        countdownUseCase.stopTimer()
    }

    fun askNextQuestion() {
        quizUseCase.askQuestion()
    }

    private fun startTimer() {
        setTimerLiveData(timeLimit.toString())
        countdownUseCase.execute(
            params = CountdownUseCase.Params(timeLimit),
            onComplete = { validateAnswer(null) },
            onNext = { remaining -> setTimerLiveData(remaining.toString()) },
            onError = {
                onError(it)
            }
        )
    }

    fun validateAnswer(selectedArtistId: Int?) {
        quizUseCase.validateAnswer(selectedArtistId, timerLiveData.value!!.toInt())
        countdownUseCase.dispose()
    }

    private fun finalizeQuest(finalScore: Int) {
        setViewState(QuizViewState.finalizeQuiz(finalScore))
        insertScoreUseCase.execute(
            params = InsertScoreUseCase.Params(finalScore),
            onComplete = {
                setViewState(QuizViewState.scorePosted())
            },
            onError = {
                onError(it)
            }
        )
    }

    fun dispose() {
        countdownUseCase.dispose()
    }

    override fun onError(t: Throwable) {
        t.printStackTrace()
        setViewState(QuizViewState.error(t))
    }
}