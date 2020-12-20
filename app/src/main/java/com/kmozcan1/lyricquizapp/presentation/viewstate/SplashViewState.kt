package com.kmozcan1.lyricquizapp.presentation.viewstate

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
data class SplashViewState (
        val isLoading: Boolean = false,
        val hasError: Boolean = false,
        val errorMessage: String? = null,
        val isSuccess: Boolean = false,
) {
    companion object {
        fun onLoading() : SplashViewState = SplashViewState(
                isLoading = true,
                isSuccess = false
        )

        fun onSuccess() : SplashViewState = SplashViewState(
                hasError = false,
                isLoading = false,
                isSuccess = true,
        )

        fun onError(e: Throwable): SplashViewState = SplashViewState(
                hasError = true,
                errorMessage = e.message,
                isSuccess = false
        )
    }

}