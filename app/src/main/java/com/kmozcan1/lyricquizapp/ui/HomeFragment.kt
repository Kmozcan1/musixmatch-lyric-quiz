package com.kmozcan1.lyricquizapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.databinding.HomeFragmentBinding
import com.kmozcan1.lyricquizapp.presentation.viewstate.HomeViewState
import com.kmozcan1.lyricquizapp.presentation.adapter.ScoreListAdapter
import com.kmozcan1.lyricquizapp.presentation.adapter.setAdapterWithCustomDivider
import com.kmozcan1.lyricquizapp.presentation.viewmodel.HomeViewModel
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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.homeViewState.observe(viewLifecycleOwner, viewStateObserver())
        viewModel.getUserProfile()
        if (getIsConnectedToInternet()) {
            showConnectionWarning(false)
        } else {
            showConnectionWarning(true)
        }
    }

    private fun viewStateObserver() = Observer<HomeViewState> { viewState ->
        when {
            viewState.hasError -> {
                makeToast(viewState.errorMessage)
            }
            viewState.isLoading -> {
            }
            viewState.isSuccess -> {
                if (viewState.hasUserProfile) {
                    setSupportActionBar(true,
                            getString(R.string.welcome, viewState.userName))
                    scoreListAdapter = ScoreListAdapter(
                            viewState.scoreList
                    )
                    binding.userQuizHistoryRecyclerView.setAdapterWithCustomDivider(
                            LinearLayoutManager(context),
                            scoreListAdapter)
                }
            }
        }
    }

    fun onLeaderBoardButton(v: View) {
        findNavController().navigate(R.id.action_homeFragment_to_leaderboardFragment)
    }

    fun onStartQuizButton(v: View) {
        findNavController().navigate(R.id.action_homeFragment_to_quizFragment)
    }

    fun onLogoutButton(v: View) {
        viewModel.logout()
        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }

    private fun showConnectionWarning(isVisible: Boolean) {
        if (isVisible) {
            binding.homeInternetTextView.visibility = View.VISIBLE
            binding.startQuizButton.visibility = View.GONE
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