package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.interactor.base.CompletableUseCase
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.ScoreDomainModel
import com.kmozcan1.lyricquizapp.domain.repository.ScoreRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
class InsertScoreUseCase @Inject constructor(
    private val scoreRepository: ScoreRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : CompletableUseCase<InsertScoreUseCase.Params>() {
    data class Params(val score: Int)

    override fun buildObservable(params: Params?): Completable {
        return getCurrentUserUseCase.buildObservable().flatMapCompletable { userName ->
            scoreRepository.insertScoreToDatabase(ScoreDomainModel(userName, params!!.score))
        }
    }

}