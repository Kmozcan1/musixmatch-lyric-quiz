package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.interactor.CountdownUseCase.Params
import com.kmozcan1.lyricquizapp.domain.interactor.base.ObservableUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * UseCase that counts from [Params.timeLimitInSeconds] down to 0.
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
class CountdownUseCase @Inject constructor() : ObservableUseCase<Long, Params>() {
    data class Params(
        val timeLimitInSeconds: Long
    )
    
    private lateinit var timerObservable: Observable<Long>

    override fun buildObservable(params: Params?): Observable<Long> {
        timerObservable =  Observable.zip(
            Observable.range(0, params!!.timeLimitInSeconds.toInt()),
            Observable.interval(0, 1, TimeUnit.SECONDS)
        ) { integer, _ ->
            params.timeLimitInSeconds - integer
        }
        return timerObservable
    }

}