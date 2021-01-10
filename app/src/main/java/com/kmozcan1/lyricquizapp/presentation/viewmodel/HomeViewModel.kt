package com.kmozcan1.lyricquizapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.kmozcan1.lyricquizapp.domain.interactor.GetUserProfileUseCase
import com.kmozcan1.lyricquizapp.presentation.viewstate.HomeViewState

class HomeViewModel @ViewModelInject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase): BaseViewModel<HomeViewState>() {

    init {
        setViewState(HomeViewState.init())
    }

    fun getUserProfile() {
        setViewState(HomeViewState.loading())
        getUserProfileUseCase.execute(
            onSuccess = { userProfile ->
                if (userProfile.scoreHistory != null) {
                    setViewState(HomeViewState.userProfile(userProfile.userName,
                            userProfile.scoreHistory))
                }
            },
            onError = {
                onError(it)
            }
        )
    }

    override fun onError(t: Throwable) {
        t.printStackTrace()
        setViewState(HomeViewState.error(t))
    }
}