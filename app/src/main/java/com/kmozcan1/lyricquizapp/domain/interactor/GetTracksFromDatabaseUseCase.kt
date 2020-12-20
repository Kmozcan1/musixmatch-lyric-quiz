package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.interactor.base.SingleUseCase
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.TrackDomainModel
import com.kmozcan1.lyricquizapp.domain.repository.TrackRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
class GetTracksFromDatabaseUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) :
    SingleUseCase<List<TrackDomainModel>, GetTracksFromDatabaseUseCase.Params>() {
    data class Params(val default: Void?)

    override fun buildObservable(params: Params?): Single<List<TrackDomainModel>> {
        return trackRepository.getAllTracksFromDatabase().map { response ->
            response.mapNotNull { TrackDomainModel(it.trackId, it.artistId, it.artistName) }
        }
    }
}