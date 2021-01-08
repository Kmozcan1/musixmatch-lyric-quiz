package com.kmozcan1.lyricquizapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kmozcan1.lyricquizapp.domain.interactor.GetCurrentUserUseCase
import com.kmozcan1.lyricquizapp.domain.interactor.RegisterUserUseCase
import com.kmozcan1.lyricquizapp.domain.interactor.UpdateCurrentUserUseCase
import com.kmozcan1.lyricquizapp.presentation.viewstate.LoginViewState

class LoginViewModel @ViewModelInject constructor(
    private val updateCurrentUserUseCase: UpdateCurrentUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase
    ): BaseViewModel<LoginViewState>() {

    fun login(userName: String) {
        updateCurrentUserUseCase.execute(
            UpdateCurrentUserUseCase.Params(userName),
            onComplete = {
                registerUser(userName)
            },
            onError = {
                onError(it)
            }
        )
    }

    private fun registerUser(userName: String) {
        registerUserUseCase.execute(
            RegisterUserUseCase.Params(userName),
            onComplete = {
                setViewState(LoginViewState.login())
            },
            onError = {
                onError(it)
            }
        )
    }

    override fun onError(t: Throwable) {
        t.printStackTrace()
        setViewState(LoginViewState.error(t))
    }


}

