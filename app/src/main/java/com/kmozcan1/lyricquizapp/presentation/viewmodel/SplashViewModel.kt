package com.kmozcan1.lyricquizapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kmozcan1.lyricquizapp.domain.Constants
import com.kmozcan1.lyricquizapp.domain.enumeration.Country
import com.kmozcan1.lyricquizapp.domain.interactor.AddTracksToDatabaseUseCase
import com.kmozcan1.lyricquizapp.domain.interactor.GetCurrentUserUseCase
import com.kmozcan1.lyricquizapp.domain.interactor.GetTracksFromApiUseCase
import com.kmozcan1.lyricquizapp.domain.interactor.NukeTracksFromDatabaseUseCase
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.TrackDomainModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.LoginViewState
import com.kmozcan1.lyricquizapp.presentation.viewstate.SplashViewState

class SplashViewModel @ViewModelInject constructor(
    private val getTracksFromApiUseCase: GetTracksFromApiUseCase,
    private val addTracksToDatabaseUseCase: AddTracksToDatabaseUseCase,
    private val nukeTracksFromDatabaseUseCase: NukeTracksFromDatabaseUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    // LiveData to observe ViewState
    val splashViewState: LiveData<SplashViewState>
        get() = _splashViewState
    private val _splashViewState = MutableLiveData<SplashViewState>()
    private fun setSplashViewState(value: SplashViewState) {
        _splashViewState.postValue(value)
    }

    // Nukes the database because chart changes all the time
    // TODO maybe there's a table specification provided by Room to delete all entries upon exit?
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

    // TODO use composite UseCase for track fetching & saving operations
    // Fetches the top tracks from the API
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

    // Saves the tracks fetched from API into the local DB
    private fun addTracksToDatabase(trackList: List<TrackDomainModel>) {
        addTracksToDatabaseUseCase.execute(
            params = AddTracksToDatabaseUseCase.Params(trackList),
            onComplete = { checkIfLoggedIn() },
            onError = {
                it.printStackTrace()
                setSplashViewState(SplashViewState.onError(it))
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
                            setSplashViewState(SplashViewState.onLoggedIn(true))
                        }
                        else -> {
                            setSplashViewState(SplashViewState.onLoggedIn(false))
                        }
                    }
                },
                onError = {
                    it.printStackTrace()
                    setSplashViewState(SplashViewState.onError(it))
                }
        )
    }
}