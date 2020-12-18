package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.enumeration.Country
import com.kmozcan1.lyricquizapp.domain.interactor.base.SingleUseCase
import com.kmozcan1.lyricquizapp.domain.manager.QuizManager
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.TrackDomainModel
import io.reactivex.rxjava3.core.Single
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 17-Dec-20.
 */

class GenerateQuizUseCase @Inject constructor(
        private val getTracksFromChartUseCase: GetTracksFromChartUseCase,
        private val getLyricsUseCase: GetLyricsUseCase,
        private val quizManager: QuizManager
) : SingleUseCase<Int, GenerateQuizUseCase.Params>() {
    data class Params(
            val country: Country,
            val questionCount: Int
    )

    override fun buildObservable(params: Params?): Single<Int> {
        getTracksFromChartUseCase.buildObservable(
            GetTracksFromChartUseCase.Params(params!!.country, params.questionCount))
                .flatMap {
                    getLyricsUseCase.buildObservable(
                        GetLyricsUseCase.Params(quizManager.selectTracks(it, params.questionCount))
                    )
                }
        return Single.just(1)
    }
}