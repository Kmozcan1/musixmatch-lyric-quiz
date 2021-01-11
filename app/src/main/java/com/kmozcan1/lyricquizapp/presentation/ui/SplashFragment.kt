package com.kmozcan1.lyricquizapp.presentation.ui

import android.os.Build
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.databinding.SplashFragmentBinding
import com.kmozcan1.lyricquizapp.presentation.viewmodel.SplashViewModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.SplashViewState
import com.kmozcan1.lyricquizapp.presentation.viewstate.SplashViewState.State.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashFragmentBinding, SplashViewModel>() {

    companion object {
        fun newInstance() = SplashFragment()
        const val DOT_STRING = "."
    }

    override val layoutId = R.layout.splash_fragment

    override val viewModelClass: Class<SplashViewModel> = SplashViewModel::class.java

    private val task: Job by lazy { loadingAnimation() }

    override fun onViewBound() {
        setSupportActionBar(false)
        showBottomNavigation(false)

        //TODO this aligns the cold launch image with splash fragment image. Need to test with more devices
        if (Build.MANUFACTURER != "Google") {
            binding.frameLayout.fitsSystemWindows = true
        }
    }

    override fun observe() {
        viewModel.viewState.observe(viewLifecycleOwner, splashViewStateObserver())
    }

    private fun splashViewStateObserver() =  Observer<SplashViewState> { viewState ->
        when(viewState.state) {
            ERROR -> {
                makeToast(viewState.errorMessage)
                navController.navigateUp()
            }
            LOADING -> {
                task.start()
            }
            LOGIN_CHECK -> {
                task.cancel()
                when {
                    viewState.isLoggedIn -> {
                        navController.navigate(R.id.action_splashFragment_to_viewPagerFragment)
                    }
                    !viewState.isLoggedIn -> {
                        navController.navigate(R.id.action_splashFragment_to_loginFragment)
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

    // Animate text view dots
    private fun loadingAnimation(): Job = viewLifecycleOwner.lifecycleScope.launch{
        var counter = 0
        binding.loadingTextView.visibility = View.VISIBLE
        while(true) {
            binding.loadingTextView.text =
                    getString(R.string.fetching_tracks_param, DOT_STRING.repeat(counter % 4))
            counter++
            delay(500)
        }
    }
}