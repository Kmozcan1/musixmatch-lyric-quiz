package com.kmozcan1.lyricquizapp.domain.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Created by Kadir Mert Özcan on 15-Dec-20.
 */
interface SharedPreferencesRepository {
    fun updateCurrentUser(userName: String) : Completable
    fun getCurrentUser() : Single<String>
}