package com.kmozcan1.lyricquizapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kmozcan1.lyricquizapp.domain.Constants
import com.kmozcan1.lyricquizapp.domain.enumeration.Country
import com.kmozcan1.lyricquizapp.domain.interactor.AddTracksToDatabaseUseCase
import com.kmozcan1.lyricquizapp.domain.interactor.GetTracksFromApiUseCase
import com.kmozcan1.lyricquizapp.domain.interactor.NukeTracksFromDatabaseUseCase
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.TrackDomainModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.SplashViewState

class SplashViewModel @ViewModelInject constructor(
    private val getTracksFromApiUseCase: GetTracksFromApiUseCase,
    private val addTracksToDatabaseUseCase: AddTracksToDatabaseUseCase,
    private val nukeTracksFromDatabaseUseCase: NukeTracksFromDatabaseUseCase
) : ViewModel() {

    // LiveData to observe ViewState
    val splashViewState: LiveData<SplashViewState>
        get() = _splashViewState
    private val _splashViewState = MutableLiveData<SplashViewState>()
    private fun setSplashViewState(value: SplashViewState) {
        _splashViewState.postValue(value)
    }

    private fun copyTrackFromApiToDatabase() {
        var trackList = mutableListOf<TrackDomainModel>()
        getTracksFromApiUseCase.execute(
            params = GetTracksFromApiUseCase.Params(
                Country.US,
                Constants.NUMBER_OF_TRACKS_TO_FETCH
            ),
            onNext = { tracks ->
                trackList.addAll(tracks)
            },
            onComplete = {
                addTracksToDatabase(trackList)
            },
            onError = {
                it.printStackTrace()
                setSplashViewState(SplashViewState.onError(it))
            }
        )
    }

    fun nukeTracksFromDatabase() {
        nukeTracksFromDatabaseUseCase.execute(
            null,
            onComplete = { copyTrackFromApiToDatabase() },
            onError = {
                it.printStackTrace()
                setSplashViewState(SplashViewState.onError(it))
            }
        )
    }

    private fun addTracksToDatabase(trackList: List<TrackDomainModel>) {
        addTracksToDatabaseUseCase.execute(
            params = AddTracksToDatabaseUseCase.Params(trackList),
            onComplete = { setSplashViewState(SplashViewState.onSuccess()) },
            onError = {
                it.printStackTrace()
                setSplashViewState(SplashViewState.onError(it))
            }
        )
    }
}