package com.kmozcan1.lyricquizapp.domain.repository

import com.kmozcan1.lyricquizapp.data.apimodel.ALyricsObject
import io.reactivex.rxjava3.core.Single

/**
 * Repository making LyricApi calls. Acts as a gateway between the Data and Domain layers.
 *
 * Created by Kadir Mert Özcan on 18-Dec-20.
 */
interface LyricsRepository {
    // returns a list of ALyricsObjects for each track in the list
    fun getLyricsForTrack(tracks: Int): Single<ALyricsObject?>
}