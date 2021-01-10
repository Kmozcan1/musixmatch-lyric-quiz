package com.kmozcan1.lyricquizapp.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.databinding.HomeFragmentBinding
import com.kmozcan1.lyricquizapp.presentation.adapter.ScoreListAdapter
import com.kmozcan1.lyricquizapp.presentation.adapter.setAdapter
import com.kmozcan1.lyricquizapp.presentation.viewmodel.HomeViewModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.HomeViewState
import com.kmozcan1.lyricquizapp.presentation.viewstate.HomeViewState.State.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var scoreListAdapter: ScoreListAdapter

    override val layoutId: Int = R.layout.home_fragment

    override val viewModelClass: Class<HomeViewModel> = HomeViewModel::class.java

    override fun onViewBound() {
        binding.homeFragment = this
        showBottomNavigation(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (getIsConnectedToInternet()) {
            showConnectionWarning(false)
        } else {
            showConnectionWarning(true)
        }

    }

    override fun observe() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
    }

    private fun viewStateObserver() = Observer<HomeViewState> { viewState ->
        when (viewState.state){
            ERROR -> {
                makeToast(viewState.errorMessage)
            }
            INIT -> {
                viewModel.getUserProfile()
            }
            LOADING -> {
                binding.homeProgressBar.visibility = View.VISIBLE
            }
            USER_PROFILE -> {
                // Hide progress bar
                binding.homeProgressBar.visibility = View.GONE
                // Set list items
                scoreListAdapter = ScoreListAdapter(
                        viewState.scoreList
                )
                binding.userQuizHistoryRecyclerView.setAdapter(
                        LinearLayoutManager(context),
                        scoreListAdapter)
            }
        }
    }

    fun onStartQuizButton(v: View) {
        navController.navigate(R.id.action_viewPagerFragment_to_quizFragment)
    }

    private fun showConnectionWarning(isVisible: Boolean) {
        if (isVisible) {
            binding.homeInternetTextView.visibility = View.VISIBLE
            binding.startQuizButton.visibility = View.INVISIBLE
        } else {
            binding.homeInternetTextView.visibility = View.GONE
            binding.startQuizButton.visibility = View.VISIBLE
        }
    }

    override fun onInternetConnected() {
        showConnectionWarning(false)
    }

    override fun onInternetDisconnected() {
        showConnectionWarning(true)
    }
}