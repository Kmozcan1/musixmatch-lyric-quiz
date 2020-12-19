package com.kmozcan1.lyricquizapp.domain.repository

import com.kmozcan1.lyricquizapp.domain.enumeration.Country
import com.kmozcan1.lyricquizapp.data.apimodel.InlineResponse2006MessageBody
import com.kmozcan1.lyricquizapp.data.db.entity.TrackEntity
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.TrackDomainModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Repository making TrackApi calls. Acts as a gateway between the Data and Domain layers.
 *
 * Created by Kadir Mert Özcan on 17-Dec-20.
 */

interface TrackRepository {
    fun getTracksFromChart(country: Country, pageSize: Int, page: Int): Single<List<InlineResponse2006MessageBody>?>
    fun insertTrackToDatabase(track: TrackDomainModel): Completable
    fun getAllTracksFromDatabase(): Single<List<TrackEntity>>
}