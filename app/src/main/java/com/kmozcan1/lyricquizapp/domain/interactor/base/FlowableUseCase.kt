package com.kmozcan1.lyricquizapp.domain.interactor.base

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
abstract class FlowableUseCase<Result, in Params> : Disposable {
    private lateinit var disposable: Disposable

    abstract fun buildObservable(params: Params?): Flowable<Result>

    fun execute(params: Params?,
                onComplete: () -> Unit = {},
                onNext: Consumer<Result>? = Consumer {  },
                onError: Consumer<Throwable>? = Consumer {  }) {
        disposable = buildObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(onNext)
            .doOnComplete(onComplete)
            .doOnError(onError)
            .subscribe()
    }

    override fun dispose() {
        return disposable.dispose()
    }

    override fun isDisposed(): Boolean {
        return disposable.isDisposed
    }
}