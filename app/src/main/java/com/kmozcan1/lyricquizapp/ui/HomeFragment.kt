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
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var scoreListAdapter: ScoreListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(
            inflater, container, false
        )
        binding.homeFragment = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.homeViewState.observe(viewLifecycleOwner, viewStateObserver())
        viewModel.getUserProfile()
    }

    private fun viewStateObserver() = Observer<HomeViewState> {
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
                                binding.userQuizHistoryRecyclerView.setAdapterWithCustomDivider(
                                    LinearLayoutManager(context),
                                    scoreListAdapter)
                            }
                        }
                    }
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

}