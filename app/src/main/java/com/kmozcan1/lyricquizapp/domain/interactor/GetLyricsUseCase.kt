package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.interactor.base.SingleUseCase
import com.kmozcan1.lyricquizapp.domain.mapper.LyricsRepositoryToDomainModelMapper
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.LyricsDomainModel
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.TrackDomainModel
import com.kmozcan1.lyricquizapp.domain.repository.LyricsRepository
import io.reactivex.rxjava3.core.Single
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 18-Dec-20.
 */
class GetLyricsUseCase @Inject constructor(
        private val lyricsRepository: LyricsRepository,
        private val lyricsRepositoryToDomainModelMapper: LyricsRepositoryToDomainModelMapper,
) : SingleUseCase<LyricsDomainModel, GetLyricsUseCase.Params>() {
    data class Params(
            val trackList: List<TrackDomainModel>
    )

    override fun buildObservable(params: Params?): Single<LyricsDomainModel> {
        return lyricsRepository.getLyricsForTracks(params!!.trackList)
                .map {
                    response -> lyricsRepositoryToDomainModelMapper.map(response)
                }
    }
}