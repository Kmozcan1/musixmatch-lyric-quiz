package com.kmozcan1.lyricquizapp.presentation.viewstate

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
data class SplashViewState (
    val state: State,
    val errorMessage: String? = null,
    val isLoggedIn: Boolean = false
) {
    companion object {
        fun loading() : SplashViewState = SplashViewState(
            state = State.LOADING
        )

        fun error(e: Throwable): SplashViewState = SplashViewState(
            state = State.ERROR,
            errorMessage = e.message,
        )

        fun loginCheck(isLoggedIn: Boolean) : SplashViewState = SplashViewState(
            state = State.LOGIN_CHECK,
            isLoggedIn = isLoggedIn
        )
    }

    enum class State {
        LOADING,
        ERROR,
        LOGIN_CHECK
    }

}