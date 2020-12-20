package com.kmozcan1.lyricquizapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kmozcan1.lyricquizapp.domain.enumeration.Country
import com.kmozcan1.lyricquizapp.domain.enumeration.QuizDifficulty
import com.kmozcan1.lyricquizapp.domain.interactor.CountdownUseCase
import com.kmozcan1.lyricquizapp.domain.interactor.GenerateQuizUseCase
import com.kmozcan1.lyricquizapp.domain.interactor.InsertScoreUseCase
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.Question
import com.kmozcan1.lyricquizapp.presentation.viewstate.QuizViewState
import kotlin.properties.Delegates

class QuizViewModel @ViewModelInject constructor(
    private val createQuizUseCase: GenerateQuizUseCase,
    private val countdownUseCase: CountdownUseCase,
    private val insertScoreUseCase: InsertScoreUseCase
) : ViewModel() {

    // LiveData to observe ViewState
    val quizViewState: LiveData<QuizViewState>
        get() = _quizViewState
    private val _quizViewState = MutableLiveData<QuizViewState>()
    private fun setQuizViewState(value: QuizViewState) {
        _quizViewState.postValue(value)
    }

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
        setQuizViewState(QuizViewState.onLoading())
        createQuizUseCase.execute(
            params = GenerateQuizUseCase.Params(Country.US, QuizDifficulty.DEFAULT),
            onSuccess = {
                quiz ->
                questionList = quiz.questions
                timeLimit = quiz.timeLimit
                setQuizViewState(QuizViewState.onSuccess())
                setScoreLiveData(0.toString())
                askQuestion()
            },
            onError = {
                it.printStackTrace()
                setQuizViewState(QuizViewState.onError(it))
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
                it.printStackTrace()
                setQuizViewState(QuizViewState.onError(it))
            }
        )
    }

    //TODO move the app logic to the domain layer
    fun checkAnswer(selectedOption: String) {
        countdownUseCase.dispose()
        if (selectedOption == questionLiveData.value?.correctAnswer?.name) {
            setScoreLiveData((scoreLiveData.value?.toInt()?.plus(timerLiveData.value?.toInt()!!)).toString())
        }
        askQuestion()
    }

    private fun finalizeQuest() {
        setQuizViewState(QuizViewState.onQuizFinished())
        scoreLiveData.value?.let { score ->
            InsertScoreUseCase.Params(score.toInt()) }?.let { params ->
            insertScoreUseCase.execute(
                params = params,
                onComplete = {
                    setQuizViewState(QuizViewState.onScorePosted())
                },
                onError = {
                    it.printStackTrace()
                    setQuizViewState(QuizViewState.onError(it))
                }
            )
        }
    }
}