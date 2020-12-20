package com.kmozcan1.lyricquizapp.domain.interactor.base

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber


/**
 * Created by Kadir Mert Özcan on 15-Dec-20.
 *
 * Base class for interactors that return [Observable] objects
 * */
abstract class ObservableUseCase<Result, in Params> : Disposable {
    private lateinit var disposable: Disposable

    abstract fun buildObservable(params: Params?): Observable<Result>

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
            .subscribe({}, Timber::w)
    }

    override fun dispose() {
        return disposable.dispose()
    }

    override fun isDisposed(): Boolean {
        return disposable.isDisposed
    }
}
