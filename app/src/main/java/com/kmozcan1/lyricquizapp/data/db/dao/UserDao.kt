package com.kmozcan1.lyricquizapp.data.db.dao

import androidx.room.*
import com.kmozcan1.lyricquizapp.data.db.entity.UserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserEntity(userName: String) : Completable

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE userName = :userName")
    fun getUserEntity(userName: String): Single<List<UserEntity>>

    @Delete
    fun deleteUserEntity(user: UserEntity) : Completable

    @Update
    fun updateUserEntity(user: UserEntity) : Completable
}