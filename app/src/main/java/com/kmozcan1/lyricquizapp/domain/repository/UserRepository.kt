package com.kmozcan1.lyricquizapp.domain.repository

import com.kmozcan1.lyricquizapp.data.db.entity.UserEntity
import com.kmozcan1.lyricquizapp.domain.model.UserDomainModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
interface UserRepository {
    fun insertUserEntity(userName: String) : Completable

    fun getUserEntity(userName: String): Single<UserEntity>

    fun deleteUserEntity(userModel: UserDomainModel) : Completable

    fun updateUserEntity(userModel: UserDomainModel) : Completable
}