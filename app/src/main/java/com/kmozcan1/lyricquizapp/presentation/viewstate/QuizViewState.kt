package com.kmozcan1.lyricquizapp.presentation.viewstate

import com.kmozcan1.lyricquizapp.domain.model.Answer
import com.kmozcan1.lyricquizapp.domain.model.Question

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
data class QuizViewState (
    val state: State,
    val errorMessage: String? = null,
    val question: Question? = null,
    val answer: Answer? = null,
    val finalScore: Int? = null
) {
    companion object {
        fun loading() : QuizViewState = QuizViewState(
            state = State.LOADING
        )

        fun error(e: Throwable): QuizViewState = QuizViewState(
            state = State.ERROR,
            errorMessage = e.message,
        )

        fun quizGenerated(): QuizViewState = QuizViewState(
            state = State.QUIZ_GENERATED,
        )

        fun question(question: Question): QuizViewState = QuizViewState(
            state = State.QUESTION,
            question = question
        )

        fun answer(answer: Answer): QuizViewState = QuizViewState(
            state = State.ANSWER,
            answer = answer
        )

        fun finalizeQuiz(finalScore: Int) : QuizViewState = QuizViewState(
            state = State.QUIZ_FINISHED,
            finalScore = finalScore
        )

        fun scorePosted() : QuizViewState = QuizViewState(
            state = State.SCORE_POSTED
        )
    }

    enum class State {
        ERROR,
        LOADING,
        QUIZ_GENERATED,
        QUESTION,
        ANSWER,
        SCORE_POSTED,
        QUIZ_FINISHED
    }
}