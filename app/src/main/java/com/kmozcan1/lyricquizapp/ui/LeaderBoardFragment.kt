package com.kmozcan1.lyricquizapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.presentation.viewmodel.LeaderBoardViewModel

class LeaderBoardFragment : Fragment() {

    companion object {
        fun newInstance() = LeaderBoardFragment()
    }

    private lateinit var viewModel: LeaderBoardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.leaderboard_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LeaderBoardViewModel::class.java)
        // TODO: Use the ViewModel
    }

}