package com.kmozcan1.lyricquizapp.application.di

import android.app.Activity
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.ui.BaseFragment
import com.kmozcan1.lyricquizapp.ui.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent


/**
 * Created by Kadir Mert Özcan on 25-Dec-20.
 */

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun providesMainActivity(
            activity: Activity
    ): MainActivity {
        return activity as MainActivity
    }

}