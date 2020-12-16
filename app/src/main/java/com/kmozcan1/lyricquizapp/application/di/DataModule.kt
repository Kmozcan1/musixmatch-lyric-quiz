package com.kmozcan1.lyricquizapp.application.di

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.kmozcan1.lyricquizapp.data.SharedPreferencesDataSourceImpl
import com.kmozcan1.lyricquizapp.domain.repository.SharedPreferencesRepository
import com.kmozcan1.lyricquizapp.data.repository.SharedPreferencesRepositoryImpl
import com.kmozcan1.lyricquizapp.domain.datasource.SharedPreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext


/**
 * Created by Kadir Mert Özcan on 14-Dec-20.
 */

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {
    @Provides
    fun provideObjectMapper(): ObjectMapper =
         ObjectMapper().registerModule(KotlinModule())

    @Provides
    fun provideSharedPreferencesDataSource(
        @ApplicationContext context: Context
    ) : SharedPreferencesDataSource =
        SharedPreferencesDataSourceImpl(context)

    @Provides
    fun provideSharedPreferencesRepository(
        sharedPreferencesDataSource: SharedPreferencesDataSource,
        objectMapper: ObjectMapper
    ) : SharedPreferencesRepository =
        SharedPreferencesRepositoryImpl(sharedPreferencesDataSource, objectMapper)
}