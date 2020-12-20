package com.kmozcan1.lyricquizapp.domain.model.viewstate

import com.kmozcan1.lyricquizapp.domain.model.domainmodel.Question

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
data class MainViewState (
        val isLoading: Boolean = false,
        val hasError: Boolean = false,
        val errorMessage: String? = null,
        val isSuccess: Boolean = false,
) {
    companion object {
        fun onLoading() : MainViewState = MainViewState(
                isLoading = true,
                isSuccess = false
        )

        fun onSuccess() : MainViewState = MainViewState(
                hasError = false,
                isLoading = false,
                isSuccess = true,
        )

        fun onError(e: Throwable): MainViewState = MainViewState(
                hasError = true,
                errorMessage = e.message,
                isSuccess = false
        )
    }

}