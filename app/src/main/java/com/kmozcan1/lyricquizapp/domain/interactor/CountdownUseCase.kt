package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.interactor.CountdownUseCase.Params
import com.kmozcan1.lyricquizapp.domain.interactor.base.ObservableUseCase
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject


/**
 * UseCase that counts from [Params.timeLimitInSeconds] down to 0.
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
class CountdownUseCase @Inject constructor() : ObservableUseCase<Long, Params>() {
    data class Params(
        val timeLimitInSeconds: Long
    )

    private var stopped: AtomicBoolean = AtomicBoolean()
    
    private lateinit var timerObservable: Observable<Long>

    override fun buildObservable(params: Params?): Observable<Long> {
        stopped.set(false)
        timerObservable =  Observable.zip(
            Observable.range(1, params!!.timeLimitInSeconds.toInt())
                .takeWhile { !stopped.get() },
            Observable.interval(1, 1, TimeUnit.SECONDS)
                .takeWhile { !stopped.get() }
        ) { timeElapsed, _ ->
            params.timeLimitInSeconds - timeElapsed
        }
        return timerObservable
    }

    fun stopTimer() {
        stopped.set(true)
    }

}