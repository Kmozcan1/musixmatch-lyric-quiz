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
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.databinding.HomeFragmentBinding
import com.kmozcan1.lyricquizapp.databinding.LeaderboardFragmentBinding
import com.kmozcan1.lyricquizapp.presentation.adapter.ScoreListAdapter
import com.kmozcan1.lyricquizapp.presentation.adapter.setAdapterWithCustomDivider
import com.kmozcan1.lyricquizapp.presentation.viewmodel.LeaderBoardViewModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.HomeViewState
import com.kmozcan1.lyricquizapp.presentation.viewstate.LeaderBoardViewState
import com.kmozcan1.lyricquizapp.presentation.viewstate.LeaderBoardViewState.State.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaderBoardFragment : BaseFragment<LeaderboardFragmentBinding, LeaderBoardViewModel>() {

    companion object {
        fun newInstance() = LeaderBoardFragment()
    }

    private lateinit var scoreListAdapter: ScoreListAdapter

    override fun layoutId(): Int = R.layout.leaderboard_fragment

    override fun getViewModelClass(): Class<LeaderBoardViewModel> = LeaderBoardViewModel::class.java

    override fun onViewBound() {
        setSupportActionBar(true, getString(R.string.leader_board))
    }

    override fun observe() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
        viewModel.getTopScores()
    }

    private fun viewStateObserver() = Observer<LeaderBoardViewState> { viewState ->
        when (viewState.state){
            ERROR -> {
                makeToast(viewState.errorMessage)
                navController.navigateUp()
            }
            LOADING -> {
            }
            SCORE_LIST -> {
                viewState.scoreList?.let {
                    scoreListAdapter = ScoreListAdapter(viewState.scoreList)
                    binding.leaderBoardRecyclerView.setAdapterWithCustomDivider(
                            LinearLayoutManager(context),
                            scoreListAdapter)
                }
            }
        }
    }
}