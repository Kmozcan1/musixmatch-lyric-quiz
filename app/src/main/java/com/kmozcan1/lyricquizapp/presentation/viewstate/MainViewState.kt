package com.kmozcan1.lyricquizapp.presentation.viewstate

/**
 * Created by Kadir Mert Özcan on 25-Dec-20.
 */
data class MainViewState(
        val isLoading: Boolean = false,
        val hasError: Boolean = false,
        val errorMessage: String? = null,
        val onConnectionChange: Boolean = false,
        val isConnected: Boolean = false
) {
    companion object {
        fun onError(e: Throwable): MainViewState = MainViewState(
                hasError = true,
                errorMessage = e.message,
        )

        fun onConnectionChange(isConnected: Boolean) : MainViewState = MainViewState(
                onConnectionChange = true,
                isConnected = isConnected
        )
    }


}
