package com.kmozcan1.lyricquizapp.data.db.dao

import androidx.room.*
import com.kmozcan1.lyricquizapp.data.db.entity.TrackEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrackEntity(entity:TrackEntity) : Completable

    @Query("SELECT * FROM ${TrackEntity.TABLE_NAME}")
    fun getAllTrackEntities(): Single<List<TrackEntity>>

    @Delete
    fun deleteTrackEntity(person:TrackEntity) : Completable

    @Update
    fun updateTrackEntity(person:TrackEntity) : Completable
}