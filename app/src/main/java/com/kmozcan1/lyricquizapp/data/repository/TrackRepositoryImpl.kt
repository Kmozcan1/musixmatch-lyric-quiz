package com.kmozcan1.lyricquizapp.data.repository

import com.kmozcan1.lyricquizapp.data.api.musixmatch.TrackApi
import com.kmozcan1.lyricquizapp.domain.enumeration.Country
import com.kmozcan1.lyricquizapp.domain.model.apimodel.InlineResponse2009
import com.kmozcan1.lyricquizapp.domain.repository.TrackRepository
import io.reactivex.rxjava3.core.Single
import java.math.BigDecimal
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
        private const val hasLyrics = "1";
    }

    override fun getTracksFromChart(
        page: BigDecimal,
        pageSize: BigDecimal,
        country: Country
    ): Single<InlineResponse2009> {
        return trackApi.chartTracksGetGet(
            page = page,
            pageSize = pageSize,
            country = country.stringValue,
            fHasLyrics = hasLyrics,
            callback = null,
            format = null,
        )
    }
}