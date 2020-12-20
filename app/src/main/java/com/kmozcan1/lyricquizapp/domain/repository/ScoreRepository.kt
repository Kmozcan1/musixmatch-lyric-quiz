package com.kmozcan1.lyricquizapp.domain.repository

import com.kmozcan1.lyricquizapp.data.db.entity.ScoreEntity
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.ScoreDomainModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
interface ScoreRepository {
    fun insertScoreToDatabase(scoreModel: ScoreDomainModel): Completable

    fun getLastTenUserScoresFromDatabase(userName: String): Single<List<ScoreEntity>>

    fun getTopScoresFromDatabase(): Single<List<ScoreEntity>>
}