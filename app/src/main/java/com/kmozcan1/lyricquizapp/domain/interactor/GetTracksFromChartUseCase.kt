package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.enumeration.Country
import com.kmozcan1.lyricquizapp.domain.interactor.base.SingleUseCase
import com.kmozcan1.lyricquizapp.domain.model.Track
import com.kmozcan1.lyricquizapp.domain.repository.TrackRepository
import io.reactivex.rxjava3.core.Single
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 17-Dec-20.
 */
class GetTracksFromChartUseCase @Inject constructor(
    private val trackRepository: TrackRepository
    ) : SingleUseCase<List<Track>, GetTracksFromChartUseCase.Params>() {
    data class Params(
        val page: BigDecimal,
        val pageSize: BigDecimal,
        val country: Country
        )

    override fun buildObservable(params: Params?): Single<List<Track>> {
        TODO("Not yet implemented")
    }

}