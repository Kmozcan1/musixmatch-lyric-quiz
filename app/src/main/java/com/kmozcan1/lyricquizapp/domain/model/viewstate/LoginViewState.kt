package com.kmozcan1.lyricquizapp.domain.model.viewstate

/**
 * Created by Kadir Mert Özcan on 16-Dec-20.
 */
data class LoginViewState (
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null
) {
    companion object {
        fun success() : LoginViewState = LoginViewState(
            hasError = false,
            isLoading = false,
        )

        fun error(e: Throwable): LoginViewState = LoginViewState(
            hasError = true,
            errorMessage = e.message,
        )
    }


    }