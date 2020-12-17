package com.kmozcan1.lyricquizapp.domain.repository

import com.kmozcan1.lyricquizapp.domain.enumeration.Country
import com.kmozcan1.lyricquizapp.domain.model.apimodel.InlineResponse2009
import io.reactivex.rxjava3.core.Single
import java.math.BigDecimal

/**
 * Repository making TrackApi calls. Acts as a gateway between the Data and Domain layers.
 *
 * Created by Kadir Mert Özcan on 17-Dec-20.
 */

interface TrackRepository {
    fun getTracksFromChart(page: BigDecimal, pageSize: BigDecimal, country: Country): Single<InlineResponse2009>
}