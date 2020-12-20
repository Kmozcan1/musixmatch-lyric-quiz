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
    private val registerUserUseCase: RegisterUserUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
): ViewModel() {

    // LiveData to observe ViewState
    val loginViewState: LiveData<LoginViewState>
        get() = _loginViewState
    private val _loginViewState = MutableLiveData<LoginViewState>()
    private fun setLoginViewState(value: LoginViewState) {
        _loginViewState.postValue(value)
    }

    fun checkIfLoggedIn () {
        getCurrentUserUseCase.execute(
            GetCurrentUserUseCase.Params(),
            onSuccess = { userName ->
                if (userName.isNotBlank()) {
                    setLoginViewState(LoginViewState.onLoggedIn(true))
                }
            },
            onError = {
                it.printStackTrace()
                setLoginViewState(LoginViewState.onError(it))
            }
        )
    }

    fun login(userName: String) {
        updateCurrentUserUseCase.execute(
            UpdateCurrentUserUseCase.Params(userName),
            onComplete = {
                registerUser(userName)
            },
            onError = {
                it.printStackTrace()
                setLoginViewState(LoginViewState.onError(it))
            })
    }

    private fun registerUser(userName: String) {
        registerUserUseCase.execute(
            RegisterUserUseCase.Params(userName),
            onComplete = {
                setLoginViewState(LoginViewState.isLoginSuccess(true))
            },
            onError = {
                setLoginViewState(LoginViewState.onError(it))
            }
        )
    }


}

