package com.kmozcan1.lyricquizapp.ui

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
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashFragmentBinding, SplashViewModel>() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    override fun layoutId() = R.layout.splash_fragment

    override fun getViewModelClass(): Class<SplashViewModel> = SplashViewModel::class.java

    override fun onViewBound() {
        binding.quizFragment = this
        binding.frameLayout.visibility = View.VISIBLE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setSupportActionBar(false)
        viewModel.splashViewState.observe(viewLifecycleOwner, splashViewStateObserver())
        if (getIsConnectedToInternet()) {
            viewModel.prepareTracks()
        } else {
            showConnectionWarning(true)
        }
    }

    private fun splashViewStateObserver() =  Observer<SplashViewState> { viewState ->
        when {
            viewState.hasError -> {
                makeToast(viewState.errorMessage)
                findNavController().navigateUp()
            }
            viewState.isSuccess -> {
                when {
                    viewState.isLoggedIn -> {
                        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                    }
                    !viewState.isLoggedIn -> {
                        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                    }
                }
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