package com.kmozcan1.lyricquizapp.domain.model.viewstate

/**
 * Created by Kadir Mert Özcan on 16-Dec-20.
 */
data class LoginViewState (
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val isLoggedIn: Boolean = false,
    var isLoginSuccess: Boolean = false
) {
    companion object {
        fun onSuccess() : LoginViewState = LoginViewState(
            isSuccess = true
        )

        fun onError(e: Throwable): LoginViewState = LoginViewState(
            hasError = true,
            errorMessage = e.message,
        )

        fun onLoggedIn(isLoggedIn: Boolean) : LoginViewState = LoginViewState(
            isSuccess = true,
            isLoggedIn = isLoggedIn
        )

        fun isLoginSuccess(isLoginSuccess: Boolean) : LoginViewState = LoginViewState(
            isSuccess = true,
            isLoginSuccess = true
        )
    }


}