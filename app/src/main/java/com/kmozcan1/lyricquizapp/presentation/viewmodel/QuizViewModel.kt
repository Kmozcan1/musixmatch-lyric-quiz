package com.kmozcan1.lyricquizapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kmozcan1.lyricquizapp.domain.enumeration.Country
import com.kmozcan1.lyricquizapp.domain.enumeration.QuizDifficulty
import com.kmozcan1.lyricquizapp.domain.interactor.CountdownUseCase
import com.kmozcan1.lyricquizapp.domain.interactor.GenerateQuizUseCase
import com.kmozcan1.lyricquizapp.domain.interactor.InsertScoreUseCase
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.Question
import com.kmozcan1.lyricquizapp.presentation.viewstate.QuizViewState
import kotlin.properties.Delegates

class QuizViewModel @ViewModelInject constructor(
    private val generateQuizUserCase: GenerateQuizUseCase,
    private val countdownUseCase: CountdownUseCase,
    private val insertScoreUseCase: InsertScoreUseCase
) : BaseViewModel<QuizViewState>() {
    // LiveData to observe the question and options
    val questionLiveData: LiveData<Question>
        get() = _questionLiveData
    private val _questionLiveData = MutableLiveData<Question>()
    private fun setQuestionLiveData(value: Question) {
        _questionLiveData.postValue(value)
    }

    // LiveData to observe the timer
    val timerLiveData: LiveData<String>
        get() = _timerLiveData
    private val _timerLiveData = MutableLiveData<String>()
    private fun setTimerLiveData(value: String) {
        _timerLiveData.postValue(value)
    }

    // LiveData to observe the score
    val scoreLiveData: LiveData<String>
        get() = _scoreLiveData
    private val _scoreLiveData = MutableLiveData<String>()
    private fun setScoreLiveData(value: String) {
        _scoreLiveData.postValue(value)
    }

    private lateinit var questionList: List<Question>

    private var timeLimit by Delegates.notNull<Long>()

    private var questionIndex = 0


    // Creates the quest and starts asking questions onSuccess
    fun createQuiz() {
        setViewState(QuizViewState.loading())
        generateQuizUserCase.execute(
            params = GenerateQuizUseCase.Params(),
            onSuccess = { quiz ->
                questionList = quiz.questions
                timeLimit = quiz.timeLimit
                setViewState(QuizViewState.quizGenerated())
                setScoreLiveData(0.toString())
                askQuestion()
            },
            onError = {
                onError(it)
            }
        )
    }

    private fun askQuestion() {
        if (questionIndex == questionList.size) {
            finalizeQuest()
        } else {
            startTimer()
            val currentQuestion = questionList[questionIndex]
            setQuestionLiveData(currentQuestion)
            questionIndex++
        }

    }

    private fun startTimer() {
        countdownUseCase.execute(
            params = CountdownUseCase.Params(timeLimit),
            onComplete = { askQuestion() },
            onNext = { remaining -> setTimerLiveData(remaining.toString()) },
            onError = {
                onError(it)
            }
        )
    }

    //TODO move the app logic to the domain layer maybe?
    fun checkAnswer(selectedOption: String) {
        countdownUseCase.dispose()
        if (selectedOption == questionLiveData.value?.correctAnswer?.name) {
            setScoreLiveData((scoreLiveData.value?.toInt()?.plus(timerLiveData.value?.toInt()!!)).toString())
        }
        askQuestion()
    }

    private fun finalizeQuest() {
        setViewState(QuizViewState.quizFinished())
        scoreLiveData.value?.let { score ->
            InsertScoreUseCase.Params(score.toInt()) }?.let { params ->
            insertScoreUseCase.execute(
                params = params,
                onComplete = {
                    setViewState(QuizViewState.scorePosted())
                },
                onError = {
                    onError(it)
                }
            )
        }
    }

    fun dispose() {
        countdownUseCase.dispose()
    }

    override fun onError(t: Throwable) {
        t.printStackTrace()
        setViewState(QuizViewState.error(t))
    }
}