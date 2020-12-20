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
import com.kmozcan1.lyricquizapp.databinding.LeaderboardFragmentBinding
import com.kmozcan1.lyricquizapp.presentation.adapter.ScoreListAdapter
import com.kmozcan1.lyricquizapp.presentation.adapter.setAdapterWithCustomDivider
import com.kmozcan1.lyricquizapp.presentation.viewmodel.LeaderBoardViewModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.HomeViewState
import com.kmozcan1.lyricquizapp.presentation.viewstate.LeaderBoardViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaderBoardFragment : Fragment() {

    companion object {
        fun newInstance() = LeaderBoardFragment()
    }

    private lateinit var viewModel: LeaderBoardViewModel
    private lateinit var binding: LeaderboardFragmentBinding
    private lateinit var scoreListAdapter: ScoreListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LeaderboardFragmentBinding.inflate(
            inflater, container, false
        )
        binding.leaderBoardFragment = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LeaderBoardViewModel::class.java)
        viewModel.leaderBoardViewState.observe(viewLifecycleOwner, viewStateObserver())
        viewModel.getTopScores()
    }

    private fun viewStateObserver() = Observer<LeaderBoardViewState> {
        it?.let { viewState ->
            when {
                viewState.hasError -> {
                    Toast.makeText(
                        this.activity,
                        viewState.errorMessage,
                        Toast.LENGTH_LONG
                    )
                        .show()
                    findNavController().navigateUp()
                }
                viewState.isLoading -> {
                }
                viewState.isSuccess -> {
                    when {
                        viewState.hasScoreList  -> {
                            if (viewState.scoreList != null) {
                                scoreListAdapter = ScoreListAdapter(
                                    viewState.scoreList
                                )
                                binding.leaderBoardRecyclerView.setAdapterWithCustomDivider(
                                    LinearLayoutManager(context),
                                    scoreListAdapter)
                            }
                        }
                    }
                }
            }
        }
    }

}