package com.kmozcan1.lyricquizapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.databinding.SplashFragmentBinding
import com.kmozcan1.lyricquizapp.presentation.viewmodel.SplashViewModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.SplashViewState
import com.kmozcan1.lyricquizapp.presentation.viewstate.SplashViewState.State.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashFragmentBinding, SplashViewModel>() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    override fun layoutId() = R.layout.splash_fragment

    override fun getViewModelClass(): Class<SplashViewModel> = SplashViewModel::class.java

    override fun onViewBound() {
        setSupportActionBar(false)
    }

    override fun observe() {
        viewModel.splashViewState.observe(viewLifecycleOwner, splashViewStateObserver())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (getIsConnectedToInternet()) {
            viewModel.prepareTracks()
        }
    }

    private fun splashViewStateObserver() =  Observer<SplashViewState> { viewState ->
        when(viewState.state) {
            ERROR -> {
                makeToast(viewState.errorMessage)
                navController.navigateUp()
            }
            LOGIN_CHECK -> {
                when {
                    viewState.isLoggedIn -> {
                        navController.navigate(R.id.action_splashFragment_to_homeFragment)
                    }
                    !viewState.isLoggedIn -> {
                        navController.navigate(R.id.action_splashFragment_to_loginFragment)
                    }
                }
            }
            LOADING -> {

            }
        }
    }

    private fun showConnectionWarning(isVisible: Boolean) {
        if (isVisible) {
            binding.splashInternetTextView.visibility = View.VISIBLE
        } else {
            binding.splashInternetTextView.visibility = View.GONE
        }
    }

    override fun onInternetConnected() {
        showConnectionWarning(false)
        viewModel.prepareTracks()
    }

    override fun onInternetDisconnected() {
        showConnectionWarning(true)
    }
}