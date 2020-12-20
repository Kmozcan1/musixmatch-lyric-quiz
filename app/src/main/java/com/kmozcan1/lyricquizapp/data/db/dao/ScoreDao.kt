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

    @Query("SELECT * FROM ${ScoreEntity.TABLE_NAME} WHERE userName = :userName ORDER BY score LIMIT 10" )
    fun getLastTenUserScores(userName: String): Single<List<ScoreEntity>>

    @Query("SELECT * FROM ${ScoreEntity.TABLE_NAME} ORDER BY score DESC LIMIT 25")
    fun getTopScores(): Single<List<ScoreEntity>>

}