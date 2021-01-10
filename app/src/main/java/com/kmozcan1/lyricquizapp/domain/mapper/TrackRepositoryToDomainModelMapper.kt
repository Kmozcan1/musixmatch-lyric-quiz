package com.kmozcan1.lyricquizapp.domain.mapper

import com.kmozcan1.lyricquizapp.data.apimodel.ATrackObject
import com.kmozcan1.lyricquizapp.domain.model.TrackDomainModel
import javax.inject.Inject

/**
 * Maps the repository model to domain model in order to get rid of
 *
 * Created by Kadir Mert Özcan on 17-Dec-20.
 */
class TrackRepositoryToDomainModelMapper @Inject constructor()
    : Mapper<ATrackObject, TrackDomainModel> {
    override fun map(repositoryModel: ATrackObject) =
        repositoryModel.run {
            TrackDomainModel(
                trackId = if (trackId == null) 0 else trackId!!.toInt(),
                artistId = if (artistId == null) 0 else artistId!!.toInt(),
                artistName = if (artistName == null) "" else artistName!!
            )
        }
}