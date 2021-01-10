package com.kmozcan1.lyricquizapp.domain.interactor.base

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.CompletableSubject
import timber.log.Timber

/**
 * Created by Kadir Mert Özcan on 15-Dec-20.
 *
 * Base class for interactors that return [Completable] objects
 */
abstract class CompletableUseCase<in Params> : Disposable {
    private val disposables: CompositeDisposable = CompositeDisposable()

    abstract fun buildObservable(params: Params? = null): Completable

    fun execute(params: Params? = null, onComplete: () -> Unit = {}, onError: Consumer<Throwable>? = null) {
        disposables += buildObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete(onComplete)
            .doOnError(onError)
            .subscribe({}, Timber::w)
    }

    override fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    override fun isDisposed(): Boolean {
        return disposables.isDisposed
    }

    internal fun onChildObservableError(error: Throwable, subject: CompletableSubject) {
        if (!isDisposed) {
            subject.onError(error)
        }
    }
}