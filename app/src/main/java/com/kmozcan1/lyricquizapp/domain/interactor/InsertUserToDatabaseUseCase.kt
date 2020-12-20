package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.interactor.base.CompletableUseCase
import com.kmozcan1.lyricquizapp.domain.repository.TrackRepository
import com.kmozcan1.lyricquizapp.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
class InsertUserToDatabaseUseCase @Inject constructor(
    private val userRepository: UserRepository
) : CompletableUseCase<InsertUserToDatabaseUseCase.Params>() {
    data class Params(val userName: String)

    override fun buildObservable(params: Params?): Completable {
        return userRepository.insertUserEntity(params!!.userName)
    }
}