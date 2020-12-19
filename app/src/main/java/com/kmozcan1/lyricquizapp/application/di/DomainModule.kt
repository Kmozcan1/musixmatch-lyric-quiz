package com.kmozcan1.lyricquizapp.application.di

import com.kmozcan1.lyricquizapp.domain.factory.QuizManagerFactory
import com.kmozcan1.lyricquizapp.domain.factory.QuizManagerFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun bindQuizManagerFactory(quizManagerFactory: QuizManagerFactoryImpl): QuizManagerFactory
}