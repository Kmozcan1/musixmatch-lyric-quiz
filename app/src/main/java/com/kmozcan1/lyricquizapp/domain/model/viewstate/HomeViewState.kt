package com.kmozcan1.lyricquizapp.domain.model.viewstate

import com.kmozcan1.lyricquizapp.domain.model.domainmodel.ScoreDomainModel

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
data class HomeViewState (
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val userName: String? = null,
    val hasScoreList: Boolean = false,
    val scoreList: List<ScoreDomainModel>? = null
) {
    companion object {
        fun onSuccess() : HomeViewState = HomeViewState(
            hasError = false,
            isLoading = false,
            isSuccess = true
        )

        fun onError(e: Throwable): HomeViewState = HomeViewState(
            hasError = true,
            errorMessage = e.message
        )

        fun onUserName(userName: String) : HomeViewState = HomeViewState(
            hasError = false,
            isLoading = false,
            isSuccess = true,
            userName = userName
        )

        fun onScoreList(scoreList: List<ScoreDomainModel>) : HomeViewState = HomeViewState(
            hasError = false,
            isLoading = false,
            isSuccess = true,
            hasScoreList = true,
            scoreList = scoreList
        )
    }


}