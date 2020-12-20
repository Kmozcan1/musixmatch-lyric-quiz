package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.interactor.base.SingleUseCase
import com.kmozcan1.lyricquizapp.domain.repository.SharedPreferencesRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
class GetCurrentUserUseCase @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : SingleUseCase<String, GetCurrentUserUseCase.Params>() {
    data class Params(val void: Void? = null)

    override fun buildObservable(params: Params?): Single<String> {
        return sharedPreferencesRepository.getCurrentUser()
    }
}