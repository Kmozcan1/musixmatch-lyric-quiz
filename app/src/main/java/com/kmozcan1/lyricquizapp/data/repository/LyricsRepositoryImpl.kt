package com.kmozcan1.lyricquizapp.data.repository

import com.kmozcan1.lyricquizapp.data.api.musixmatch.LyricsApi
import com.kmozcan1.lyricquizapp.data.apimodel.ALyricsObject
import com.kmozcan1.lyricquizapp.domain.repository.LyricsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 18-Dec-20.
 */
class LyricsRepositoryImpl @Inject constructor(
        private val lyricsApi: LyricsApi,
        private val apiKey: String
): LyricsRepository {
    override fun getLyricsForTrack(trackId: Int): Single<ALyricsObject> {
        return lyricsApi.trackLyricsGetGet(
                trackId = trackId.toString(),
                apiKey = apiKey
        ).map { response ->
            response.message?.body?.lyrics?.trackId = trackId
            response.message?.body?.lyrics
        }
    }
}