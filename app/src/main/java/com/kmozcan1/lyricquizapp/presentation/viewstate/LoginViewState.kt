package com.kmozcan1.lyricquizapp.presentation.viewstate

/**
 * Created by Kadir Mert Özcan on 16-Dec-20.
 */
data class LoginViewState (
    val state: State,
    val errorMessage: String? = null,
    var isLoginSuccess: Boolean = false
) {
    companion object {

        fun error(e: Throwable): LoginViewState = LoginViewState(
            state = State.ERROR,
            errorMessage = e.message
        )

        fun loading(): LoginViewState = LoginViewState(
            state = State.LOADING
        )

        fun login(isLoginSuccess: Boolean) : LoginViewState = LoginViewState(
            state = State.LOGIN,
            isLoginSuccess = isLoginSuccess
        )
    }

    enum class State {
        ERROR,
        LOADING,
        LOGIN
    }
}