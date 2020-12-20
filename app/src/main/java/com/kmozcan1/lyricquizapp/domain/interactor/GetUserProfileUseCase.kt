package com.kmozcan1.lyricquizapp.domain.interactor

import androidx.room.rxjava3.EmptyResultSetException
import com.kmozcan1.lyricquizapp.domain.interactor.base.SingleUseCase
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.ScoreDomainModel
import com.kmozcan1.lyricquizapp.domain.repository.ScoreRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
class GetUserProfileUseCase @Inject constructor(
    private val getUserScoreHistoryUseCase: GetUserScoreHistoryUseCase,
    private val getUserFromDatabaseUseCase: GetUserFromDatabaseUseCase,
    private val insertUserToDatabaseUseCase: InsertUserToDatabaseUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : SingleUseCase<List<ScoreDomainModel>,
        GetUserProfileUseCase.Params>() {
    data class Params(val void: Void? = null)

    override fun buildObservable(params: Params?): Single<List<ScoreDomainModel>> {
        return getCurrentUserUseCase.buildObservable().flatMap { userName ->
            getUserScoreHistoryUseCase.buildObservable(
                GetUserScoreHistoryUseCase.Params(userName))
                .onErrorResumeNext { error ->
                    if (error is EmptyResultSetException) Single.just(emptyList())
                    else Single.error(error)
            }
        }
    }
}