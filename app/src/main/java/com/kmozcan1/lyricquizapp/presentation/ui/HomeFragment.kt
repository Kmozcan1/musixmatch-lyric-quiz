package com.kmozcan1.lyricquizapp.presentation.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.databinding.HomeFragmentBinding
import com.kmozcan1.lyricquizapp.presentation.viewstate.HomeViewState
import com.kmozcan1.lyricquizapp.presentation.adapter.ScoreListAdapter
import com.kmozcan1.lyricquizapp.presentation.adapter.setAdapterWithCustomDivider
import com.kmozcan1.lyricquizapp.presentation.viewmodel.HomeViewModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.HomeViewState.State.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var scoreListAdapter: ScoreListAdapter

    override fun layoutId(): Int = R.layout.home_fragment

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

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
        viewModel.getUserProfile()
    }

    private fun viewStateObserver() = Observer<HomeViewState> { viewState ->
        when (viewState.state){
            ERROR -> {
                makeToast(viewState.errorMessage)
            }
            LOADING -> {
                binding.profileProgressBar.visibility = View.VISIBLE
            }
            USER_PROFILE -> {
                // Hide progress bar
                binding.profileProgressBar.visibility = View.GONE

                // Set ActionBar title
                setSupportActionBar(true,
                        getString(R.string.welcome, viewState.userName))
                // Set list items
                scoreListAdapter = ScoreListAdapter(
                        viewState.scoreList
                )
                binding.userQuizHistoryRecyclerView.setAdapterWithCustomDivider(
                        LinearLayoutManager(context),
                        scoreListAdapter)
            }
        }
    }

    fun onStartQuizButton(v: View) {
        navController.navigate(R.id.action_homeFragment_to_quizFragment)
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