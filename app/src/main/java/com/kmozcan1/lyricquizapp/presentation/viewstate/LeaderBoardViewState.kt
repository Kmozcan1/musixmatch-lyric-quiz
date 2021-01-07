package com.kmozcan1.lyricquizapp.presentation.viewstate

import com.kmozcan1.lyricquizapp.domain.model.domainmodel.ScoreDomainModel

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
data class LeaderBoardViewState  (
    val state: State,
    val errorMessage: String? = null,
    val scoreList: List<ScoreDomainModel>? = null
)  {

    companion object {
        fun error(e: Throwable): LeaderBoardViewState = LeaderBoardViewState(
            state = State.ERROR,
            errorMessage = e.message
        )

        fun scoreList(scoreList: List<ScoreDomainModel>) : LeaderBoardViewState = LeaderBoardViewState(
            state = State.SCORE_LIST,
            scoreList = scoreList
        )

        fun loading(): LeaderBoardViewState = LeaderBoardViewState(
            state = State.LOADING
        )
    }


    enum class State {
        ERROR,
        LOADING,
        SCORE_LIST
    }
}