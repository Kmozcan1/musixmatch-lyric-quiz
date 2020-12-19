package com.kmozcan1.lyricquizapp.data.repository

import com.kmozcan1.lyricquizapp.data.db.QuizDatabase
import com.kmozcan1.lyricquizapp.data.db.entity.UserEntity
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.UserDomainModel
import com.kmozcan1.lyricquizapp.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepositoryImpl  @Inject constructor(private val quizDatabase: QuizDatabase)
    : UserRepository {

    override fun insertUserEntity(userName: String): Completable {
        return quizDatabase.userDao().insertUserEntity(userName)
    }

    override fun getUserEntity(userName: String): Single<List<UserEntity>> {
        return quizDatabase.userDao().getUserEntity(userName)
    }

    override fun deleteUserEntity(userModel: UserDomainModel): Completable {
        TODO("Not yet implemented")
    }

    override fun updateUserEntity(userModel: UserDomainModel): Completable {
        TODO("Not yet implemented")
    }
}