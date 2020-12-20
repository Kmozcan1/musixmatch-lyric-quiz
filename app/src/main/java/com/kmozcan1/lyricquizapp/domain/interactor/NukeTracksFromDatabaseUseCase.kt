package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.interactor.base.CompletableUseCase
import com.kmozcan1.lyricquizapp.domain.repository.TrackRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
class NukeTracksFromDatabaseUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) : CompletableUseCase<NukeTracksFromDatabaseUseCase.Params>() {
    data class Params(val void: Void? = null)

    override fun buildObservable(params: Params?): Completable {
        return trackRepository.nukeTable()
    }

}