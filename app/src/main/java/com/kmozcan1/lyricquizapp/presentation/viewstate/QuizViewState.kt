package com.kmozcan1.lyricquizapp.presentation.viewstate

import com.kmozcan1.lyricquizapp.domain.model.domainmodel.Question

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
data class QuizViewState (
    val state: State,
    val errorMessage: String? = null
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
            state = State.QUIZ_GENERATED
        )

        fun scorePosted() : QuizViewState = QuizViewState(
            state = State.SCORE_POSTED
        )

        fun quizFinished() : QuizViewState = QuizViewState(
            state = State.QUIZ_FINISHED
        )
    }

    enum class State {
        ERROR,
        LOADING,
        QUIZ_GENERATED,
        SCORE_POSTED,
        QUIZ_FINISHED
    }
}