package com.kmozcan1.lyricquizapp.presentation.viewstate

import com.kmozcan1.lyricquizapp.domain.model.domainmodel.Question

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
data class QuizViewState (
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val isScorePosted: Boolean = false
) {

    private lateinit var question: Question

    companion object {
        fun onLoading() : QuizViewState = QuizViewState(
            isLoading = true
        )

        fun onSuccess() : QuizViewState = QuizViewState(
            isSuccess = true
        )

        fun onError(e: Throwable): QuizViewState = QuizViewState(
            hasError = true,
            errorMessage = e.message,
            isSuccess = false
        )

        fun onScorePosted() : QuizViewState = QuizViewState(
            isSuccess = true,
            isScorePosted = true
        )

    }
}