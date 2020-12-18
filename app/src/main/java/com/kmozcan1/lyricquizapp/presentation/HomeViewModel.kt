package com.kmozcan1.lyricquizapp.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.kmozcan1.lyricquizapp.domain.Constants
import com.kmozcan1.lyricquizapp.domain.enumeration.Country
import com.kmozcan1.lyricquizapp.domain.interactor.GenerateQuizUseCase

class HomeViewModel @ViewModelInject constructor(
    private val createQuizUseCase: GenerateQuizUseCase
): ViewModel() {

    fun test() {
        createQuizUseCase.execute(
            GenerateQuizUseCase.Params(Country.US, Constants.NUMBER_OF_TRACKS_TO_FETCH),
            { }, { }
        )
    }
}