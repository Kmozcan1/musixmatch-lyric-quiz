package com.kmozcan1.lyricquizapp.application.di

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.kmozcan1.lyricquizapp.BuildConfig
import com.kmozcan1.lyricquizapp.data.SharedPreferencesDataSourceImpl
import com.kmozcan1.lyricquizapp.data.api.musixmatch.TrackApi
import com.kmozcan1.lyricquizapp.data.tools.GeneratedCodeConverters
import com.kmozcan1.lyricquizapp.domain.datasource.SharedPreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton


/**
 * Created by Kadir Mert Özcan on 14-Dec-20.
 */

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideObjectMapper(): ObjectMapper =
         ObjectMapper().registerModule(KotlinModule())

    @Provides
    fun provideSharedPreferencesDataSource(
        @ApplicationContext context: Context
    ) : SharedPreferencesDataSource =
        SharedPreferencesDataSourceImpl(context)
}