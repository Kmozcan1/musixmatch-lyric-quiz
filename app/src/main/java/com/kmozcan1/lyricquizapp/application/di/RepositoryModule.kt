package com.kmozcan1.lyricquizapp.application.di

import com.fasterxml.jackson.databind.ObjectMapper
import com.kmozcan1.lyricquizapp.data.api.musixmatch.LyricsApi
import com.kmozcan1.lyricquizapp.data.api.musixmatch.TrackApi
import com.kmozcan1.lyricquizapp.data.db.QuizDatabase
import com.kmozcan1.lyricquizapp.data.repository.*
import com.kmozcan1.lyricquizapp.domain.datasource.SharedPreferencesDataSource
import com.kmozcan1.lyricquizapp.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module that provides repository dependencies.
 *
 * Created by Kadir Mert Özcan on 17-Dec-20.
 */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideSharedPreferencesRepository(
        sharedPreferencesDataSource: SharedPreferencesDataSource,
        objectMapper: ObjectMapper
    ) : SharedPreferencesRepository =
        SharedPreferencesRepositoryImpl(sharedPreferencesDataSource, objectMapper)

    @Provides
    fun provideTrackRepositoryImpl(
        quizDatabase: QuizDatabase,
        trackApi: TrackApi,
        apiKey: String
    ) : TrackRepository =
        TrackRepositoryImpl(quizDatabase, trackApi, apiKey)

    @Provides
    fun provideLyricsRepositoryImpl(
        lyricsApi: LyricsApi,
        apiKey: String
    ) : LyricsRepository =
        LyricsRepositoryImpl(lyricsApi, apiKey)

    @Provides
    fun provideUserRepositoryImpl(
        quizDatabase: QuizDatabase
    ) : UserRepository =
        UserRepositoryImpl(quizDatabase)

    @Provides
    fun provideScoreRepositoryImpl(
        quizDatabase: QuizDatabase
    ) : ScoreRepository =
        ScoreRepositoryImpl(quizDatabase)
}