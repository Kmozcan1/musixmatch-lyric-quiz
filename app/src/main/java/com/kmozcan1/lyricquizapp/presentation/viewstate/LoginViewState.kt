package com.kmozcan1.lyricquizapp.presentation.viewstate

/**
 * Created by Kadir Mert Özcan on 16-Dec-20.
 */
data class LoginViewState (
    val state: State,
    val errorMessage: String? = null
) {
    companion object {

        fun error(e: Throwable): LoginViewState = LoginViewState(
            state = State.ERROR,
            errorMessage = e.message
        )

        //fun validationError(): LoginViewState = Login

        fun loading(): LoginViewState = LoginViewState(
            state = State.LOADING
        )

        fun login() : LoginViewState = LoginViewState(
            state = State.LOGIN)
    }

    enum class State {
        ERROR,
        LOADING,
        LOGIN
    }
}