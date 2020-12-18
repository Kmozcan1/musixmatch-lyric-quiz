package com.kmozcan1.lyricquizapp.data.repository

import com.kmozcan1.lyricquizapp.data.api.musixmatch.TrackApi
import com.kmozcan1.lyricquizapp.domain.Constants.MAX_PAGE_SIZE
import com.kmozcan1.lyricquizapp.domain.enumeration.Country
import com.kmozcan1.lyricquizapp.domain.model.apimodel.ATrackObject
import com.kmozcan1.lyricquizapp.domain.model.apimodel.InlineResponse2006MessageBody
import com.kmozcan1.lyricquizapp.domain.model.apimodel.InlineResponse2009
import com.kmozcan1.lyricquizapp.domain.repository.TrackRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.Single.just
import javax.inject.Inject

/**
 * TrackRepository implementation class.
 *
 * Created by Kadir Mert Özcan on 17-Dec-20.
 */
class TrackRepositoryImpl @Inject constructor(
    private val trackApi: TrackApi
): TrackRepository {
    companion object {
        private const val HAS_LYRICS = "1";
    }

    override fun getTracksFromChart(
            country: Country,
            questionCount: Int
    ): Single<List<ATrackObject>> {
        val remainder = questionCount % MAX_PAGE_SIZE
        val apiCallCount = questionCount / MAX_PAGE_SIZE
        var trackList = just(emptyList<ATrackObject>())

        var a = trackApi.chartTracksGetGet(
            page = 1.toBigDecimal(),
            pageSize = MAX_PAGE_SIZE.toBigDecimal(),
            country = country.stringValue,
            fHasLyrics = HAS_LYRICS,
            callback = "callback",
            format = "json"
        )

        var b = Single.just(1)

        b.map {
            it
        }

        // Need to make more than once call because API can only return 100 tracks at a time
        for (i in 0..apiCallCount) {
            trackList.mergeWith(trackApi.chartTracksGetGet(
                    page = i.toBigDecimal(),
                    pageSize = MAX_PAGE_SIZE.toBigDecimal(),
                    country = country.stringValue,
                    fHasLyrics = HAS_LYRICS,
                    callback = "callback",
                    format = "json"
            ).map { response -> response })
        }

        // Make one more call in case questionCount is not a divisible by 100
        if (remainder != 0) {
            trackList.mergeWith(trackApi.chartTracksGetGet(
                    page = apiCallCount.toBigDecimal().inc(), // increments by 1
                    pageSize = remainder.toBigDecimal(),
                    country = country.stringValue,
                    fHasLyrics = HAS_LYRICS,
                    callback = "callback",
                    format = "json"
            ).map { response -> response })
        }

        return trackList
    }
}