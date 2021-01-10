package com.kmozcan1.lyricquizapp.presentation.ui

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.databinding.LeaderboardFragmentBinding
import com.kmozcan1.lyricquizapp.presentation.adapter.ScoreListAdapter
import com.kmozcan1.lyricquizapp.presentation.adapter.setAdapter
import com.kmozcan1.lyricquizapp.presentation.viewmodel.LeaderBoardViewModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.LeaderBoardViewState
import com.kmozcan1.lyricquizapp.presentation.viewstate.LeaderBoardViewState.State.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaderBoardFragment : BaseFragment<LeaderboardFragmentBinding, LeaderBoardViewModel>() {

    companion object {
        fun newInstance() = LeaderBoardFragment()
    }

    private lateinit var scoreListAdapter: ScoreListAdapter

    override val layoutId: Int = R.layout.leaderboard_fragment

    override val viewModelClass: Class<LeaderBoardViewModel> = LeaderBoardViewModel::class.java

    override fun onViewBound() {
        showBottomNavigation(true)
    }

    override fun observe() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
        viewModel.getTopScores()
    }

    private fun viewStateObserver() = Observer<LeaderBoardViewState> { viewState ->
        when (viewState.state){
            ERROR -> {
                makeToast(viewState.errorMessage)
            }
            INIT -> {
                viewModel.getTopScores()
            }
            LOADING -> {

            }
            SCORE_LIST -> {
                viewState.scoreList?.let {
                    scoreListAdapter = ScoreListAdapter(viewState.scoreList)
                    binding.leaderBoardRecyclerView.setAdapter(
                            LinearLayoutManager(context),
                            scoreListAdapter)
                }
            }
        }
    }
}