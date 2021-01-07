package com.kmozcan1.lyricquizapp.application.di

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

/**
 * Created by Kadir Mert Özcan on 07-Jan-21.
 */
@Module
@InstallIn(FragmentComponent::class)
object UIModule {

    @Provides
    fun providesNavController(
        fragment: Fragment
    ): NavController {
        return fragment.findNavController()
    }
}