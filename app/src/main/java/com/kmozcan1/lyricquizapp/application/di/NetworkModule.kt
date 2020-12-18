package com.kmozcan1.lyricquizapp.application.di

import com.kmozcan1.lyricquizapp.BuildConfig
import com.kmozcan1.lyricquizapp.data.api.musixmatch.LyricsApi
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
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
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
        .host("api.musixmatch.com")
        .addPathSegment("ws")
        .addPathSegment("1.1")
        .addQueryParameter("apiKey", apiKey)
        .addPathSegment("")
        .build()


    @Provides
    @Singleton

    fun providesRxJava3CallAdapterFactory(): RxJava3CallAdapterFactory {
        return RxJava3CallAdapterFactory.create()
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

    @Provides
    @Singleton
    fun providesMoshiConverterFactory(): Converter.Factory {
        return MoshiConverterFactory.create()
    }


    // May provide the baseURL later
    @Provides
    @Singleton
    fun providesRetrofit(
        moshiConverterFactory: Converter.Factory,
        callAdapterFactory: RxJava3CallAdapterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(callAdapterFactory)
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

    @Singleton
    @Provides
    fun provideLyricsApi(
            retrofit: Retrofit
    ): LyricsApi {
        return retrofit.create(LyricsApi::class.java)
    }



}