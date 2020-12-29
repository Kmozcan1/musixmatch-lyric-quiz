package com.kmozcan1.lyricquizapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kmozcan1.lyricquizapp.domain.interactor.*
import com.kmozcan1.lyricquizapp.presentation.viewstate.HomeViewState

class HomeViewModel @ViewModelInject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateCurrentUserUseCase: UpdateCurrentUserUseCase
    ): ViewModel() {

    // LiveData to observe ViewState
    val homeViewState: LiveData<HomeViewState>
        get() = _homeViewState
    private val _homeViewState = MutableLiveData<HomeViewState>()
    private fun setHomeViewState(value: HomeViewState) {
        _homeViewState.postValue(value)
    }


    fun getUserProfile() {
        getUserProfileUseCase.execute(
            onSuccess = { userProfile ->
                if (userProfile.scoreHistory != null) {
                    setHomeViewState(HomeViewState.onUserProfile(userProfile.userName,
                            userProfile.scoreHistory))
                }
            },
            onError = {
                it.printStackTrace()
                setHomeViewState(HomeViewState.onError(it))
            }
        )
    }

    fun logout() {
        updateCurrentUserUseCase.execute(
            params = UpdateCurrentUserUseCase.Params(""),
            onComplete = {

            },
            onError = {
                it.printStackTrace()
                setHomeViewState(HomeViewState.onError(it))
            }
        )
    }
}