package com.kmozcan1.lyricquizapp.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kmozcan1.lyricquizapp.domain.interactor.UpdateCurrentUserUseCase
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.User
import com.kmozcan1.lyricquizapp.domain.model.viewstate.LoginViewState

class LoginViewModel @ViewModelInject constructor(
    private val updateCurrentUserUseCase: UpdateCurrentUserUseCase
): ViewModel() {

    val loginViewState: MutableLiveData<LoginViewState> by lazy {
        MutableLiveData<LoginViewState>()
    }

    fun updateCurrentUser(userName: String) {
        try {
            User(userName)
            updateCurrentUserUseCase.execute(
                UpdateCurrentUserUseCase.Params(userName),
                onComplete = {
                    loginViewState.value = LoginViewState.success()
                },
                onError = {
                    it.printStackTrace()
                    loginViewState.value = LoginViewState.error(it)
                })
        } catch (e: IllegalArgumentException ) {
            loginViewState.value = loginViewState.value?.copy(
                isLoading = false,
                errorMessage = e.message,
                hasError = true
            )
        }
    }


}

