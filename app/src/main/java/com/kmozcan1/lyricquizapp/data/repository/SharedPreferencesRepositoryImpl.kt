package com.kmozcan1.lyricquizapp.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.kmozcan1.lyricquizapp.domain.datasource.SharedPreferencesDataSource
import com.kmozcan1.lyricquizapp.domain.repository.SharedPreferencesRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 15-Dec-20.
 */
class SharedPreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferencesDataSource: SharedPreferencesDataSource,
    private val objectMapper: ObjectMapper
) : SharedPreferencesRepository {
    override fun updateCurrentUser(userName: String): Completable {
        return try {
            sharedPreferencesDataSource.updateCurrentUser(userName)
            Completable.complete()
        } catch (e: Exception) {
            Completable.error(e)
        }
    }
}