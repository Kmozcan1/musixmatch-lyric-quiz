package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.interactor.base.ObservableUseCase
import com.kmozcan1.lyricquizapp.domain.manager.InternetManager
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 25-Dec-20.
 */
class ObserveInternetConnectionUseCase @Inject constructor(
        private val internetManager: InternetManager
): ObservableUseCase<Boolean, ObserveInternetConnectionUseCase.Params>() {
    data class Params(val void: Void? = null)

    override fun buildObservable(params: Params?): Observable<Boolean> {
        return internetManager.getInternetState()
    }
}