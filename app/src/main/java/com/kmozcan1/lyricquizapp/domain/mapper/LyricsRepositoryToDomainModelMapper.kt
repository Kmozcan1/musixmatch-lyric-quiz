package com.kmozcan1.lyricquizapp.domain.mapper

import com.kmozcan1.lyricquizapp.data.apimodel.ALyricsObject
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.LyricsDomainModel
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 18-Dec-20.
 */
class LyricsRepositoryToDomainModelMapper @Inject constructor() : Mapper<LyricsDomainModel?,
        ALyricsObject?> {
    override fun map(lyricsRepositoryObject: ALyricsObject?) =
            lyricsRepositoryObject?.let {
                LyricsDomainModel(
                    lyricsBody = lyricsRepositoryObject.lyricsBody,
                    trackId = lyricsRepositoryObject.trackId
                )
            }
}