package com.kmozcan1.lyricquizapp.domain.mapper

import com.kmozcan1.lyricquizapp.domain.model.domainmodel.TrackDomainModel
import com.kmozcan1.lyricquizapp.domain.model.apimodel.ATrackObject
import javax.inject.Inject

/**
 * Maps the repository model to domain model in order to get rid of
 *
 * Created by Kadir Mert Özcan on 17-Dec-20.
 */
class TrackRepositoryToDomainModelMapper @Inject constructor() : Mapper<TrackDomainModel?,
        ATrackObject?> {
    override fun map(trackRepositoryObject: ATrackObject?) =
        trackRepositoryObject?.let {
            TrackDomainModel(
                trackId = trackRepositoryObject.trackId,
                lyricsId = trackRepositoryObject.lyricsId,
                artistId = trackRepositoryObject.artistId,
                artistName = trackRepositoryObject.artistName
            )
        }
}