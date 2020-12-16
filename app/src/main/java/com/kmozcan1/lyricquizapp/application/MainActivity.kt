package com.kmozcan1.lyricquizapp.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.kmozcan1.lyricquizapp.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Kadir Mert Özcan on 14-Dec-20.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
    }

    fun onClick() {

    }
}