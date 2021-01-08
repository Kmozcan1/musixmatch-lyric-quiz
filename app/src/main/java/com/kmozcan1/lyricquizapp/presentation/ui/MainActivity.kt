package com.kmozcan1.lyricquizapp.presentation.ui

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.presentation.viewmodel.MainViewModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.MainViewState
import com.kmozcan1.lyricquizapp.presentation.viewstate.MainViewState.State.*
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Kadir Mert Özcan on 14-Dec-20.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    var isConnectedToInternet: Boolean = false
        private set

    private val navHostFragment : NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    private val navController: NavController by lazy {
        navHostFragment.navController
    }

    private val bottomNavigationView: BottomNavigationView by lazy {
        findViewById(R.id.bottomNavigationView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.viewState.observe(this, observeViewState())
        viewModel.observeInternetConnection()
        setViews()
    }

    private fun setViews() {
        setContentView(R.layout.activity_main)

        // Sets the botton navigation view with the nav graph
        bottomNavigationView.setupWithNavController(navController)
    }


    private fun observeViewState() = Observer<MainViewState> { viewState ->
        when(viewState.state) {
            ERROR -> {
                makeToast(viewState.errorMessage)
            }
            CONNECTION_CHANGE -> {
                // Get the active fragment
                val baseFragment = supportFragmentManager.fragments
                    .first()?.childFragmentManager?.fragments?.get(0) as BaseFragment<*, *>

                isConnectedToInternet = viewState.isConnected
                if (viewState.isConnected) {
                    baseFragment.onInternetConnected()
                } else {
                    baseFragment.onInternetDisconnected()
                }
            }
            LOADING -> TODO()
        }
    }

    fun makeToast(toastMessage: String?) {
        Toast.makeText(
            this,
            toastMessage,
            Toast.LENGTH_LONG
        ).show()
    }

    fun showBottomNavigation(isVisible: Boolean) {
        if (isVisible) {
            bottomNavigationView.visibility = View.VISIBLE
        } else {
            bottomNavigationView.visibility = View.GONE
        }
    }
}