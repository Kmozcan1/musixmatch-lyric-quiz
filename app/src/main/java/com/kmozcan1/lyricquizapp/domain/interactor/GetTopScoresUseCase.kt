package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.interactor.base.SingleUseCase
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.ScoreDomainModel
import com.kmozcan1.lyricquizapp.domain.repository.ScoreRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
class GetTopScoresUseCase @Inject constructor(
    private val scoreRepository: ScoreRepository
) : SingleUseCase<List<ScoreDomainModel>, GetTopScoresUseCase.Params>() {
    data class Params(val void: Void? = null)

    override fun buildObservable(params: Params?): Single<List<ScoreDomainModel>> {
        return scoreRepository.getTopScoresFromDatabase().map { scores ->
            scores.map { score ->
                ScoreDomainModel(score.userName, score.score!!)
            }
        }
    }
}