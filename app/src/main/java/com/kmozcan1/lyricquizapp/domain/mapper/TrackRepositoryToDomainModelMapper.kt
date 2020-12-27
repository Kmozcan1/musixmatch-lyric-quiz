package com.kmozcan1.lyricquizapp.domain.mapper

import com.kmozcan1.lyricquizapp.domain.model.domainmodel.TrackDomainModel
import com.kmozcan1.lyricquizapp.data.apimodel.ATrackObject
import javax.inject.Inject

/**
 * Maps the repository model to domain model in order to get rid of
 *
 * Created by Kadir Mert Özcan on 17-Dec-20.
 */
class TrackRepositoryToDomainModelMapper @Inject constructor() : Mapper<TrackDomainModel?,
        ATrackObject?> {
    override fun map(repositoryModel: ATrackObject?) =
        repositoryModel?.let {
            TrackDomainModel(
                trackId = repositoryModel.trackId?.toInt(),
                artistId = repositoryModel.artistId?.toInt(),
                artistName = repositoryModel.artistName
            )
        }
}