package com.kmozcan1.lyricquizapp.data.db.dao

import androidx.room.*
import com.kmozcan1.lyricquizapp.data.db.entity.TrackEntity
import com.kmozcan1.lyricquizapp.data.db.entity.UserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserEntity(entity: UserEntity) : Completable

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE id = :userId")
    fun getUserEntity(userId: Int): Single<List<UserEntity>>

    @Delete
    fun deleteUserEntity(user: UserEntity) : Completable

    @Update
    fun updateUserEntity(user: UserEntity) : Completable
}