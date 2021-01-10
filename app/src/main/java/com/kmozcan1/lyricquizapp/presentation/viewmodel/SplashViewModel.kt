package com.kmozcan1.lyricquizapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.kmozcan1.lyricquizapp.domain.interactor.GetCurrentUserUseCase
import com.kmozcan1.lyricquizapp.domain.interactor.PrepareTracksUseCase
import com.kmozcan1.lyricquizapp.presentation.viewstate.SplashViewState

class SplashViewModel @ViewModelInject constructor(
        private val prepareTracksUseCase: PrepareTracksUseCase,
        private val getCurrentUserUseCase: GetCurrentUserUseCase
) : BaseViewModel<SplashViewState>() {

    // Saves the tracks fetched from API into the local DB
    fun prepareTracks() {
        setViewState(SplashViewState.loading())
        prepareTracksUseCase.execute(
            params = PrepareTracksUseCase.Params(),
            onComplete = { checkIfLoggedIn() },
            onError = {
                onError(it)
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
                            setViewState(
                                SplashViewState.loginCheck(true))
                        }
                        else -> {
                            setViewState(
                                SplashViewState.loginCheck(false))
                        }
                    }
                },
                onError = {
                    onError(it)
                }
        )
    }

    override fun onError(t: Throwable) {
        t.printStackTrace()
        setViewState(SplashViewState.error(t))
    }
}