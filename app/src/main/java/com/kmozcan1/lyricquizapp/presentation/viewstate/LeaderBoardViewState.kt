package com.kmozcan1.lyricquizapp.presentation.viewstate

import com.kmozcan1.lyricquizapp.domain.model.domainmodel.ScoreDomainModel

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
data class LeaderBoardViewState  (
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val hasScoreList: Boolean = false,
    val scoreList: List<ScoreDomainModel>? = null
)  {
    fun onSuccess() : LeaderBoardViewState = LeaderBoardViewState(
        hasError = false,
        isLoading = false,
        isSuccess = true
    )

    fun onError(e: Throwable): LeaderBoardViewState = LeaderBoardViewState(
        hasError = true,
        errorMessage = e.message
    )

    fun onScoreList(scoreList: List<ScoreDomainModel>) : LeaderBoardViewState = LeaderBoardViewState(
        hasError = false,
        isLoading = false,
        isSuccess = true,
        hasScoreList = true,
        scoreList = scoreList
    )
}