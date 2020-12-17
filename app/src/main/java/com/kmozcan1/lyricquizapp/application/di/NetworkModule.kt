package com.kmozcan1.lyricquizapp.application.di

import com.kmozcan1.lyricquizapp.BuildConfig
import com.kmozcan1.lyricquizapp.data.api.musixmatch.TrackApi
import com.kmozcan1.lyricquizapp.data.tools.GeneratedCodeConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Module that provides network related dependencies.
 *
 * Created by Kadir Mert Özcan on 17-Dec-20.
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    // Find a better way to do this
    private const val baseUrl = "https://api.musixmatch.com/ws/1.1/"
    private const val apiKey = "4b4b9f8a9531a63279902dc768954473"

    var url = HttpUrl.Builder()
        .scheme("https")
        .host("api.musixmatch.com/ws/1.1/")
        .addQueryParameter("apikey", apiKey)
        .build()


    @Provides
    @Singleton
    fun providesConverterFactory(): Converter.Factory {
        return GeneratedCodeConverters.converterFactory()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }else{
        OkHttpClient
            .Builder()
            .build()
    }

    // May provide the baseURL later
    @Provides
    @Singleton
    fun providesRetrofit(
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideTrackApi(
        retrofit: Retrofit
    ): TrackApi {
        return retrofit.create(TrackApi::class.java)
    }
}