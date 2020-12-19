package com.kmozcan1.lyricquizapp.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kmozcan1.lyricquizapp.domain.Constants.NUMBER_OF_TRACKS_TO_FETCH
import com.kmozcan1.lyricquizapp.domain.enumeration.Country
import com.kmozcan1.lyricquizapp.domain.interactor.AddTracksToDatabaseUseCase
import com.kmozcan1.lyricquizapp.domain.interactor.GetTracksFromApiUseCase
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.TrackDomainModel
import com.kmozcan1.lyricquizapp.domain.model.viewstate.MainViewState

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
class MainViewModel @ViewModelInject constructor(
    private val getTracksFromApiUseCase: GetTracksFromApiUseCase,
    private val addTracksToDatabaseUseCase: AddTracksToDatabaseUseCase
) : ViewModel() {

    // LiveData to observe ViewState
    val mainViewState: LiveData<MainViewState>
        get() = _mainViewState
    private val _mainViewState = MutableLiveData<MainViewState>()
    private fun setMainViewState(value: MainViewState) {
        _mainViewState.postValue(value)
    }

    fun copyTrackFromApiToDatabase() {
        var trackList = mutableListOf<TrackDomainModel>()
        getTracksFromApiUseCase.execute(
                params = GetTracksFromApiUseCase.Params(Country.US, NUMBER_OF_TRACKS_TO_FETCH),
                onNext = { tracks ->
                    trackList.addAll(tracks)
                },
                onComplete = {
                    addTracksToDatabase(trackList)
                },
                onError = {
                    it.printStackTrace()
                    setMainViewState(MainViewState.error(it))
                }
        )
    }

    private fun addTracksToDatabase(trackList: List<TrackDomainModel>) {
        addTracksToDatabaseUseCase.execute(
                params = AddTracksToDatabaseUseCase.Params(trackList),
                onComplete = { setMainViewState(MainViewState.success()) },
                onError = {
                    it.printStackTrace()
                    setMainViewState(MainViewState.error(it))
                }
        )
    }
}