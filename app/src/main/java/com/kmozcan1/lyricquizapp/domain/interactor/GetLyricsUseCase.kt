package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.interactor.base.FlowableUseCase
import com.kmozcan1.lyricquizapp.domain.mapper.LyricsRepositoryToDomainModelMapper
import com.kmozcan1.lyricquizapp.domain.model.LyricsDomainModel
import com.kmozcan1.lyricquizapp.domain.model.TrackDomainModel
import com.kmozcan1.lyricquizapp.domain.repository.LyricsRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 18-Dec-20.
 */
class GetLyricsUseCase @Inject constructor(
        private val lyricsRepository: LyricsRepository,
        private val lyricsRepositoryToDomainModelMapper: LyricsRepositoryToDomainModelMapper,
) : FlowableUseCase<LyricsDomainModel, GetLyricsUseCase.Params>() {
    data class Params(
            val trackMap: MutableMap<Int, TrackDomainModel>
    )

    override fun buildObservable(params: Params?): Flowable<LyricsDomainModel> {
        val taskList = mutableListOf<Single<LyricsDomainModel>>()
        params?.trackMap!!.forEach { (key) ->
            taskList.add(lyricsRepository.getLyricsForTrack(key).map { lyricsApiObject ->
                lyricsRepositoryToDomainModelMapper.map(lyricsApiObject)
            })
        }

        return Single.merge(taskList)
    }
}