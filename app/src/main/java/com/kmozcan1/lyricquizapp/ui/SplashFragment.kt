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
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.databinding.QuizFragmentBinding
import com.kmozcan1.lyricquizapp.databinding.SplashFragmentBinding
import com.kmozcan1.lyricquizapp.presentation.viewmodel.SplashViewModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.LoginViewState
import com.kmozcan1.lyricquizapp.presentation.viewstate.SplashViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: SplashFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SplashFragmentBinding.inflate(
            inflater, container, false
        )
        binding.quizFragment = this
        binding.frameLayout.visibility = View.VISIBLE
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        viewModel.splashViewState.observe(viewLifecycleOwner, splashViewStateObserver())
        viewModel.nukeTracksFromDatabase()
    }

    private fun splashViewStateObserver() =  Observer<SplashViewState> { viewState ->
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
            viewState.isSuccess -> {
                when {
                    viewState.isLoggedIn -> {
                        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                    }
                    !viewState.isLoggedIn -> {
                        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                    }
                }
            }
        }
    }
}