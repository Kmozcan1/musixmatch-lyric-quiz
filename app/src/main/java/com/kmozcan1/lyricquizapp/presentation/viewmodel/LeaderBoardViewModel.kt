package com.kmozcan1.lyricquizapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.kmozcan1.lyricquizapp.domain.interactor.GetTopScoresUseCase
import com.kmozcan1.lyricquizapp.presentation.viewstate.LeaderBoardViewState

class LeaderBoardViewModel @ViewModelInject constructor(
    private val getTopScoresUseCase: GetTopScoresUseCase
) : BaseViewModel<LeaderBoardViewState>() {

    fun getTopScores() {
        getTopScoresUseCase.execute(
            params = null,
            onSuccess = { scoreList ->
                setViewState(LeaderBoardViewState.scoreList(scoreList))
            },
            onError = {

            }
        )
    }

    override fun onError(t: Throwable) {
        t.printStackTrace()
        setViewState(LeaderBoardViewState.error(t))
    }
}