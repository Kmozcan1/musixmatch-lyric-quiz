package com.kmozcan1.lyricquizapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.kmozcan1.lyricquizapp.domain.interactor.GetTopScoresUseCase
import com.kmozcan1.lyricquizapp.presentation.viewstate.HomeViewState
import com.kmozcan1.lyricquizapp.presentation.viewstate.LeaderBoardViewState

class LeaderBoardViewModel @ViewModelInject constructor(
    private val getTopScoresUseCase: GetTopScoresUseCase
) : ViewModel() {
    val leaderBoardViewState: LiveData<LeaderBoardViewState>
        get() = _leaderBoardState
    private val _leaderBoardState = MutableLiveData<LeaderBoardViewState>()
    private fun setLeaderBoardState(value: LeaderBoardViewState) {
        _leaderBoardState.postValue(value)
    }

    fun getTopScores() {
        getTopScoresUseCase.execute(
            params = null,
            onSuccess = { scoreList ->
                setLeaderBoardState(LeaderBoardViewState().onScoreList(scoreList))
            },
            onError = {
                it.printStackTrace()
                setLeaderBoardState(LeaderBoardViewState().onError(it))
            }
        )
    }
}