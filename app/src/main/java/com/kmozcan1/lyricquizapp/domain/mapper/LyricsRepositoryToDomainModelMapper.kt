package com.kmozcan1.lyricquizapp.domain.mapper

import com.kmozcan1.lyricquizapp.data.apimodel.ALyricsObject
import com.kmozcan1.lyricquizapp.domain.model.LyricsDomainModel
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 18-Dec-20.
 */
class LyricsRepositoryToDomainModelMapper @Inject constructor() : Mapper<ALyricsObject,
        LyricsDomainModel> {
    override fun map(repositoryModel: ALyricsObject) =
            repositoryModel.run {
                LyricsDomainModel(
                    lyricsBody = if (lyricsBody == null) "" else lyricsBody!!,
                    trackId = if (trackId == null) 0 else trackId!!
                )
            }
}