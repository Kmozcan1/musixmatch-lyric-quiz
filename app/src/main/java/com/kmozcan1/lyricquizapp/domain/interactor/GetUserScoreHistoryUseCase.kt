package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.interactor.base.SingleUseCase
import com.kmozcan1.lyricquizapp.domain.model.ScoreDomainModel
import com.kmozcan1.lyricquizapp.domain.repository.ScoreRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
class GetUserScoreHistoryUseCase @Inject constructor(
    private val scoreRepository: ScoreRepository
) : SingleUseCase<List<ScoreDomainModel>,
        GetUserScoreHistoryUseCase.Params>() {
    data class Params(val userName: String)

    override fun buildObservable(params: Params?): Single<List<ScoreDomainModel>> {
        return scoreRepository.getLastTenUserScoresFromDatabase(params!!.userName).map { scores ->
            scores.map { score ->
                ScoreDomainModel(score.userName, score.score!!)
            }
        }
    }
}