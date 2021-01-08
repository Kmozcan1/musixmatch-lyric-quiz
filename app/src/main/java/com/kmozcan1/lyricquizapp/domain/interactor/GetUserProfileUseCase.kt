package com.kmozcan1.lyricquizapp.domain.interactor

import androidx.room.rxjava3.EmptyResultSetException
import com.kmozcan1.lyricquizapp.domain.interactor.base.SingleUseCase
import com.kmozcan1.lyricquizapp.domain.model.UserDomainModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
class GetUserProfileUseCase @Inject constructor(
    private val getUserScoreHistoryUseCase: GetUserScoreHistoryUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : SingleUseCase<UserDomainModel,
        GetUserProfileUseCase.Params>() {
    data class Params(val void: Void? = null)

    override fun buildObservable(params: Params?): Single<UserDomainModel> {
        return getCurrentUserUseCase.buildObservable().flatMap { userName ->
            getUserScoreHistoryUseCase.buildObservable(
                GetUserScoreHistoryUseCase.Params(userName))
                    .map { scoreHistory ->
                        UserDomainModel(userName = userName, scoreHistory = scoreHistory)
                    }
                    .onErrorResumeNext { error ->
                        if (error is EmptyResultSetException) Single.just(
                                UserDomainModel(userName = userName, scoreHistory = emptyList())
                        )
                        else Single.error(error)
            }
        }
    }
}