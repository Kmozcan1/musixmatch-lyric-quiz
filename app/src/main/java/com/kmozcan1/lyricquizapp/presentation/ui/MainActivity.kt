package com.kmozcan1.lyricquizapp.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.presentation.viewmodel.MainViewModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.MainViewState
import com.kmozcan1.lyricquizapp.presentation.viewstate.MainViewState.State.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 14-Dec-20.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    var isConnectedToInternet: Boolean = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.viewState.observe(this, observeViewState())
        viewModel.observeInternetConnection()
        setContentView(R.layout.activity_main)
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
        }
    }

    fun makeToast(toastMessage: String?) {
        Toast.makeText(
                this,
                toastMessage,
                Toast.LENGTH_LONG
        ).show()
    }
}