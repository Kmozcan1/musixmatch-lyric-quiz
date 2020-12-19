package com.kmozcan1.lyricquizapp.data.db.dao

import androidx.room.*
import com.kmozcan1.lyricquizapp.data.db.entity.ScoreEntity
import com.kmozcan1.lyricquizapp.data.db.entity.TrackEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
@Dao
interface ScoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertScoreEntity(entity: ScoreEntity) : Completable

    @Query("SELECT TOP(10) FROM ${ScoreEntity.TABLE_NAME} WHERE userId = :userId ORDER BY score")
    fun getLastTenUserScores(userId: Int?): Single<List<ScoreEntity>>

    @Query("SELECT TOP(20) FROM ${ScoreEntity.TABLE_NAME} ORDER BY score")
    fun getTopScores(): Single<List<ScoreEntity>>

}