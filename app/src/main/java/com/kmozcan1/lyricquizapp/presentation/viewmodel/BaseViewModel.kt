package com.kmozcan1.lyricquizapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Kadir Mert Özcan on 07-Jan-21.
 */
abstract class BaseViewModel<ViewStateClass> : ViewModel() {

    val viewState: LiveData<ViewStateClass>
        get() = _viewState
    private val _viewState = MutableLiveData<ViewStateClass>()
    internal fun setViewState(value: ViewStateClass) {
        _viewState.postValue(value)
    }

    abstract fun onError(t: Throwable)
}