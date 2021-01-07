package com.kmozcan1.lyricquizapp.presentation.viewstate

/**
 * Created by Kadir Mert Özcan on 25-Dec-20.
 */
data class MainViewState(
    val state: State,
    val errorMessage: String? = null,
    val isConnected: Boolean = false
) {
    companion object {
        fun error(e: Throwable): MainViewState = MainViewState(
            state = State.ERROR,
            errorMessage = e.message,
        )

        fun loading(): MainViewState = MainViewState(
            state = State.LOADING
        )

        fun connectionChange(isConnected: Boolean): MainViewState = MainViewState(
            state = State.CONNECTION_CHANGE,
            isConnected = isConnected
        )
    }

    enum class State {
        LOADING,
        ERROR,
        CONNECTION_CHANGE
    }

}
