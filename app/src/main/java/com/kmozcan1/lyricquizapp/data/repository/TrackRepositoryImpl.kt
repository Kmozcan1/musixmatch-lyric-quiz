package com.kmozcan1.lyricquizapp.data.repository

import com.kmozcan1.lyricquizapp.data.api.musixmatch.TrackApi
import com.kmozcan1.lyricquizapp.domain.enumeration.Country
import com.kmozcan1.lyricquizapp.data.apimodel.InlineResponse2006MessageBody
import com.kmozcan1.lyricquizapp.data.db.QuizDatabase
import com.kmozcan1.lyricquizapp.data.db.entity.TrackEntity
import com.kmozcan1.lyricquizapp.domain.model.TrackDomainModel
import com.kmozcan1.lyricquizapp.domain.repository.TrackRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * TrackRepository implementation class.
 *
 * Created by Kadir Mert Özcan on 17-Dec-20.
 */
class TrackRepositoryImpl @Inject constructor(
    private val quizDatabase: QuizDatabase,
    private val trackApi: TrackApi,
    private val apiKey: String
): TrackRepository {
    companion object {
        private const val HAS_LYRICS = "1"
    }

    override fun getTracksFromChart(
        country: Country,
        pageSize: Int,
        page: Int
    ): Single<List<InlineResponse2006MessageBody>?> {

        return trackApi.chartTracksGetGet(
            page = page.toBigDecimal(),
            pageSize = pageSize.toBigDecimal(),
            country = country.stringValue,
            fHasLyrics = HAS_LYRICS,
            callback = null,
            format = null,
            apiKey = apiKey
        ).map { response -> response.message?.body?.trackList }
    }

    override fun insertTrackToDatabase(track: TrackDomainModel): Completable {
        return quizDatabase.trackDao().insertTrackEntity(
            TrackEntity(trackId = track.trackId, artistId = track.artistId, artistName = track.artistName)
        )
    }

    override fun getAllTracksFromDatabase(): Single<List<TrackEntity>> {
        return quizDatabase.trackDao().getAllTrackEntities()
    }

    override fun nukeTable(): Completable {
        return quizDatabase.trackDao().nukeTable()
    }
}