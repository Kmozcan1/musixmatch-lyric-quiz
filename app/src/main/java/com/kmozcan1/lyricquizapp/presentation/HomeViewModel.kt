package com.kmozcan1.lyricquizapp.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kmozcan1.lyricquizapp.domain.model.viewstate.HomeViewState

class HomeViewModel @ViewModelInject constructor(
): ViewModel() {

    // LiveData to observe ViewState
    // LiveData to observe ViewState
    val homeViewState: LiveData<HomeViewState>
        get() = _homeViewState
    private val _homeViewState = MutableLiveData<HomeViewState>()
    private fun setHomeViewState(value: HomeViewState) {
        _homeViewState.postValue(value)
    }

    fun getScoreList() {

    }
}