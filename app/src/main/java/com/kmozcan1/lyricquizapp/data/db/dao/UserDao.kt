package com.kmozcan1.lyricquizapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kmozcan1.lyricquizapp.data.db.entity.UserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserEntity(userEntity: UserEntity) : Completable

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE userName = :userName LIMIT 1")
    fun getUserEntity(userName: String): Single<UserEntity>
}