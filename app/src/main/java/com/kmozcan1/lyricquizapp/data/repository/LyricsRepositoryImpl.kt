package com.kmozcan1.lyricquizapp.data.repository

import com.kmozcan1.lyricquizapp.data.api.musixmatch.LyricsApi
import com.kmozcan1.lyricquizapp.domain.model.apimodel.ALyricsObject
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.TrackDomainModel
import com.kmozcan1.lyricquizapp.domain.repository.LyricsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 18-Dec-20.
 */
class LyricsRepositoryImpl @Inject constructor(
        private val lyricsApi: LyricsApi
): LyricsRepository {
    override fun getLyricsForTracks(tracks: List<TrackDomainModel>): Single<ALyricsObject> {
        var lyricsList = lyricsApi.trackLyricsGetGet(
                trackId = tracks[0].trackId.toString(),
                callback = null,
                format = null
        ).map { response ->
            response.message?.body?.lyrics!!
        }

        for (track in tracks) {
            var a = lyricsList.mergeWith(
                    lyricsApi.trackLyricsGetGet(
                            trackId = track.trackId.toString(),
                            callback = null,
                            format = null
                    ).map { response ->
                        response.message?.body?.lyrics!!
                    })
        }

        return lyricsList
    }
}

/*
* for (track in tracks) {
            var a = lyricsList.mergeWith(
                    lyricsApi.trackLyricsGetGet(
                            trackId = track.trackId.toString(),
                            callback = null,
                            format = null
                    ).map { response ->
                        response.message?.body?.lyrics
                    }
            ).toList().map { lyrics -> {
                val lyricsList = mutableListOf<ALyricsObject>()
                for (item in lyrics) {
                    lyricsList.add(item)
                }
            }
            }
        }
*/