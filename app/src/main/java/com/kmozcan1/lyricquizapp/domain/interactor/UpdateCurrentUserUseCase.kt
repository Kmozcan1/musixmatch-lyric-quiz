package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.interactor.base.CompletableUseCase
import com.kmozcan1.lyricquizapp.domain.repository.SharedPreferencesRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 15-Dec-20.
 */
class UpdateCurrentUserUseCase @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
    ) : CompletableUseCase<UpdateCurrentUserUseCase.Params>() {

    data class Params(val userName: String)

    override fun buildObservable(params: Params?): Completable {
        return sharedPreferencesRepository.updateCurrentUser(params!!.userName)
    }
}