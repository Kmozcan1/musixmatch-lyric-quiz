package com.kmozcan1.lyricquizapp.presentation.viewstate

import com.kmozcan1.lyricquizapp.domain.model.ScoreDomainModel

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
data class HomeViewState (
    val state: State,
    val errorMessage: String? = null,
    val userName: String? = null,
    val scoreList: List<ScoreDomainModel> = emptyList()
) {
    companion object {
        fun error(e: Throwable): HomeViewState = HomeViewState(
                state = State.ERROR,
                errorMessage = e.message
        )

        fun loading(): HomeViewState = HomeViewState(
                state = State.LOADING
        )

        fun userProfile(userName: String,
                        scoreList: List<ScoreDomainModel>): HomeViewState = HomeViewState(
                state = State.USER_PROFILE,
                userName = userName,
                scoreList = scoreList
        )

    }

    enum class State {
        ERROR,
        LOADING,
        USER_PROFILE
    }
}