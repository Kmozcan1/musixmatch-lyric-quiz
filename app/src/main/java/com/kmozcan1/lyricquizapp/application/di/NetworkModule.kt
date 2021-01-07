package com.kmozcan1.lyricquizapp.application.di

import android.util.Base64
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
import javax.inject.Singleton

/**
 * Module that provides network related dependencies.
 *
 * Created by Kadir Mert Özcan on 17-Dec-20.
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    init {
        System.loadLibrary("native-lib")
    }

    private var url = HttpUrl.Builder()
        .scheme("https")
        .host("api.musixmatch.com")
        .addPathSegment("ws")
        .addPathSegment("1.1")
        .addPathSegment("") // to add "/" at the end
        .build()

    private external fun getAPIKey(): String

    @Provides
    @Singleton
    fun providesApiKey(): String {
        return getAPIKey()
    }

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
    fun providesConverterFactory(): Converter.Factory {
        return GeneratedCodeConverters.converterFactory()
    }


    // May provide the baseURL later
    @Provides
    @Singleton
    fun providesRetrofit(
            converterFactory: Converter.Factory,
            callAdapterFactory: RxJava3CallAdapterFactory,
            okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(converterFactory)
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