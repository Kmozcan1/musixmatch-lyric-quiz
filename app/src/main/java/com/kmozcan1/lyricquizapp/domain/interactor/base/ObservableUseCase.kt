package com.kmozcan1.lyricquizapp.domain.interactor.base

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.Subject
import timber.log.Timber


/**
 * Created by Kadir Mert Özcan on 15-Dec-20.
 *
 * Base class for interactors that return [Observable] objects
 * */
abstract class ObservableUseCase<Result, in Params> : Disposable {
    private lateinit var disposable: Disposable

    abstract fun buildObservable(params: Params? = null): Observable<Result>

    fun execute(params: Params? = null,
                onComplete: () -> Unit = { },
                onSubscribe: Consumer<in Disposable>? = Consumer { },
                onNext: Consumer<Result>? = Consumer {  },
                onError: Consumer<Throwable>? = Consumer {  }) {
        disposable = buildObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(onSubscribe)
            .doOnNext(onNext)
            .doOnComplete(onComplete)
            .doOnError(onError)
            .subscribe({}, Timber::w)
    }

    override fun dispose() {
        if (this::disposable.isInitialized) {
            if (!disposable.isDisposed) {
                disposable.dispose()
            }
        }
    }

    override fun isDisposed(): Boolean {
        return if (this::disposable.isInitialized) {
            disposable.isDisposed
        } else {
            true
        }
    }

    internal fun onChildObservableError(error: Throwable, subject: Subject<*>) {
        if (!isDisposed) {
            subject.onError(error)
        }
    }
}
