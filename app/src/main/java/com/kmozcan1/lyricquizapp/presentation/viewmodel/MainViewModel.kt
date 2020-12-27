package com.kmozcan1.lyricquizapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kmozcan1.lyricquizapp.domain.interactor.ObserveInternetConnectionUseCase
import com.kmozcan1.lyricquizapp.presentation.viewstate.MainViewState

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
class MainViewModel @ViewModelInject constructor(
    private val observeInternetConnectionUseCase: ObserveInternetConnectionUseCase
) : ViewModel() {
    val mainViewState: LiveData<MainViewState>
        get() = _mainViewState
    private val _mainViewState = MutableLiveData<MainViewState>()
    private fun setMainViewState(value: MainViewState) {
        _mainViewState.postValue(value)
    }

    fun observeInternetConnection() {
        observeInternetConnectionUseCase.execute(
                onComplete = {},
                onNext = { isConnected ->
                    setMainViewState(MainViewState.onConnectionChange(isConnected))
                },
                onError = {
                    it.printStackTrace()
                    setMainViewState(MainViewState.onError(it))
                }
        )
    }
}