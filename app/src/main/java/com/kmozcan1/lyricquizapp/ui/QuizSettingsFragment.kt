package com.kmozcan1.lyricquizapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.presentation.QuizSettingsViewModel

class QuizSettingsFragment : Fragment() {

    companion object {
        fun newInstance() = QuizSettingsFragment()
    }

    private lateinit var viewModel: QuizSettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quiz_settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuizSettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}