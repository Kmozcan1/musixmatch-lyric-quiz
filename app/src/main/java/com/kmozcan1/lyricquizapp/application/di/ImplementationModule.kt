package com.kmozcan1.lyricquizapp.application.di

import com.kmozcan1.lyricquizapp.data.SharedPreferencesDataSourceImpl
import com.kmozcan1.lyricquizapp.domain.datasource.SharedPreferencesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * Created by Kadir Mert Özcan on 15-Dec-20.
 */

@Module
@InstallIn(ApplicationComponent::class)
abstract class ImplementationModule {
    /*@Binds
    abstract fun bindSharedPreferencesDataSource(
        sharedPreferencesDataSource: SharedPreferencesDataSourceImpl
    ): SharedPreferencesDataSource*/
}