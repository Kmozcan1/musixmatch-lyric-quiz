package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.interactor.base.SingleUseCase
import com.kmozcan1.lyricquizapp.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
class GetUserFromDatabaseUseCase @Inject constructor(
    private val userRepository: UserRepository
) : SingleUseCase<String, GetUserFromDatabaseUseCase.Params>() {
    data class Params(val userName: String)

    override fun buildObservable(params: Params?): Single<String> {
        return userRepository.getUserEntity(params!!.userName).map { userEntity ->
            userEntity.userName
        }
    }
}