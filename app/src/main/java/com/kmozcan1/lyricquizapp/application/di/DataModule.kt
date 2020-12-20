package com.kmozcan1.lyricquizapp.application.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.kmozcan1.lyricquizapp.data.SharedPreferencesDataSourceImpl
import com.kmozcan1.lyricquizapp.data.db.QuizDatabase
import com.kmozcan1.lyricquizapp.data.db.dao.ScoreDao
import com.kmozcan1.lyricquizapp.data.db.dao.TrackDao
import com.kmozcan1.lyricquizapp.data.db.dao.UserDao
import com.kmozcan1.lyricquizapp.domain.datasource.SharedPreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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

    @Provides
    @Singleton
    internal fun provideTrackDatabase(application: Application): QuizDatabase {
        return Room.databaseBuilder(
            application,
            QuizDatabase::class.java,
            QuizDatabase.DB_NAME
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

    @Provides
    internal fun provideTrackDao(appDatabase: QuizDatabase): TrackDao {
        return appDatabase.trackDao()
    }

    @Provides
    internal fun provideUserDao(appDatabase: QuizDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    internal fun provideScoreDao(appDatabase: QuizDatabase): ScoreDao {
        return appDatabase.scoreDao()
    }
}