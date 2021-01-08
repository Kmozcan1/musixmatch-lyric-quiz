package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.Constants
import com.kmozcan1.lyricquizapp.domain.enumeration.Country
import com.kmozcan1.lyricquizapp.domain.interactor.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.subjects.CompletableSubject
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 27-Dec-20.
 */
class PrepareTracksUseCase @Inject constructor(
        private val nukeTracksFromDatabaseUseCase: NukeTracksFromDatabaseUseCase,
        private val getTracksFromApiUseCase: GetTracksFromApiUseCase,
        private val addTracksToDatabaseUseCase: AddTracksToDatabaseUseCase
): CompletableUseCase<PrepareTracksUseCase.Params>() {
    data class Params(private val void: Void? = null)

    override fun buildObservable(params: Params?): Completable {
        val prepareTracksSubject = CompletableSubject.create()
        // Nukes the tracks from local db, and then fetches the tracks from API
        // I do this because there is no API method to check if the chart is updated
        nukeTracksFromDatabaseUseCase.buildObservable().andThen {
            getTracksFromApiUseCase.buildObservable(GetTracksFromApiUseCase.Params(
                    Country.US,
                    Constants.NUMBER_OF_TRACKS_TO_FETCH
            )).flatMapCompletable { tracks ->
                // Adds each batch to database (can fetch max 100 at a time from API)
                addTracksToDatabaseUseCase.buildObservable(
                        AddTracksToDatabaseUseCase.Params(tracks)
                )
            }.doOnComplete {
                // Emits that the process is completed
                prepareTracksSubject.onComplete()
            }.subscribe()
        }.subscribe()

        return prepareTracksSubject
    }
}