package com.kmozcan1.lyricquizapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kmozcan1.lyricquizapp.domain.Constants
import com.kmozcan1.lyricquizapp.domain.enumeration.Country
import com.kmozcan1.lyricquizapp.domain.interactor.*
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.TrackDomainModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.LoginViewState
import com.kmozcan1.lyricquizapp.presentation.viewstate.SplashViewState

class SplashViewModel @ViewModelInject constructor(
        private val prepareTracksUseCase: PrepareTracksUseCase,
        private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    // LiveData to observe ViewState
    val splashViewState: LiveData<SplashViewState>
        get() = _splashViewState
    private val _splashViewState = MutableLiveData<SplashViewState>()
    private fun setSplashViewState(value: SplashViewState) {
        _splashViewState.postValue(value)
    }

    // Saves the tracks fetched from API into the local DB
    fun prepareTracks() {
        prepareTracksUseCase.execute(
            params = PrepareTracksUseCase.Params(),
            onComplete = { checkIfLoggedIn() },
            onError = {
                it.printStackTrace()
                setSplashViewState(SplashViewState.onError(it))
            }
        )
    }

    // To determine where to navigate
    private fun checkIfLoggedIn() {
        getCurrentUserUseCase.execute(
                GetCurrentUserUseCase.Params(),
                onSuccess = { userName ->
                    when {
                        userName.isNotBlank() -> {
                            setSplashViewState(SplashViewState.onLoggedIn(true))
                        }
                        else -> {
                            setSplashViewState(SplashViewState.onLoggedIn(false))
                        }
                    }
                },
                onError = {
                    it.printStackTrace()
                    setSplashViewState(SplashViewState.onError(it))
                }
        )
    }
}