package com.kmozcan1.lyricquizapp.ui

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.domain.model.viewstate.MainViewState
import com.kmozcan1.lyricquizapp.presentation.MainViewModel
import com.kmozcan1.lyricquizapp.presentation.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Kadir Mert Özcan on 14-Dec-20.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var viewState: MainViewState? = null

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // Fetch tracks and insert into db while the splash "screen" is being shown (it's just a theme)
        /*viewModel.mainViewState.observe(this, {
            it?.let { state ->
                viewState = state
                when {
                    state.hasError -> {
                        Toast.makeText(
                                this,
                                state.errorMessage,
                                Toast.LENGTH_LONG
                        ).show()
                    }
                    state.isSuccess -> {
                        setTheme(R.style.AppTheme)
                        setContentView(R.layout.activity_main)
                        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                    }
                }
        }
        })*/
        //debug
        //viewModel.copyTrackFromApiToDatabase()
        /*setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)*/
        //val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
    }





}