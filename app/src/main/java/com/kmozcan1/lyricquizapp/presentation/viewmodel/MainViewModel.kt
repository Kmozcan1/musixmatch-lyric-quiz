package com.kmozcan1.lyricquizapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.kmozcan1.lyricquizapp.domain.interactor.AddTracksToDatabaseUseCase
import com.kmozcan1.lyricquizapp.domain.interactor.GetTracksFromApiUseCase

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
class MainViewModel @ViewModelInject constructor(
    private val getTracksFromApiUseCase: GetTracksFromApiUseCase,
    private val addTracksToDatabaseUseCase: AddTracksToDatabaseUseCase
) : ViewModel() {

}