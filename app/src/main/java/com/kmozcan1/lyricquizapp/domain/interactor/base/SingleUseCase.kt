package com.kmozcan1.lyricquizapp.domain.interactor.base

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber


/**
 * Created by Kadir Mert Özcan on 15-Dec-20.
 *
 * Base class for interactors that return [Single] objects
 */
abstract class SingleUseCase<Results, in Params> : Disposable {
    private val disposables: CompositeDisposable = CompositeDisposable()

    abstract fun buildObservable(params: Params? = null): Single<Results>

    fun execute(params: Params? = null, onSuccess: Consumer<Results> ?= Consumer {  },
                onError: Consumer<Throwable> ? = Consumer {  }) {
        disposables += buildObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess(onSuccess)
            .doOnError(onError)
            .subscribe({}, Timber::w)
    }



    override fun dispose() {
        return disposables.dispose()
    }

    override fun isDisposed(): Boolean {
        return disposables.isDisposed
    }
}