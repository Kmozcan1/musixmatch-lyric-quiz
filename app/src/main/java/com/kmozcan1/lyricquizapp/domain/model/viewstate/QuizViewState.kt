package com.kmozcan1.lyricquizapp.domain.model.viewstate

import com.kmozcan1.lyricquizapp.domain.model.domainmodel.Question

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
data class QuizViewState (
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val currentQuestion: Question? = null
) {

    private lateinit var question: Question

    companion object {
        fun isLoading() : QuizViewState = QuizViewState(
            isLoading = true,
            isSuccess = false
        )

        fun success() : QuizViewState = QuizViewState(
            hasError = false,
            isLoading = false,
            isSuccess = true,
        )

        fun error(e: Throwable): QuizViewState = QuizViewState(
            hasError = true,
            errorMessage = e.message,
            isSuccess = false
        )
    }
}