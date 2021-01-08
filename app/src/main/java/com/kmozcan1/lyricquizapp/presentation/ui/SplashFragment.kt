package com.kmozcan1.lyricquizapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
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
        showBottomNavigation(false)
    }

    override fun observe() {
        viewModel.splashViewState.observe(viewLifecycleOwner, splashViewStateObserver())
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
                        val navAction =  SplashFragmentDirections
                            .actionSplashFragmentToHomeFragment(viewState.userName!!)
                        navController.navigate(navAction)
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
        //navController.navigate(R.id.action_splashFragment_to_loginFragment)
    }

    override fun onInternetDisconnected() {
        showConnectionWarning(true)
    }

}