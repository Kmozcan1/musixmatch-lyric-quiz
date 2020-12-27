package com.kmozcan1.lyricquizapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.databinding.LoginFragmentBinding
import com.kmozcan1.lyricquizapp.presentation.viewstate.LoginViewState
import com.kmozcan1.lyricquizapp.presentation.viewmodel.LoginViewModel
import com.kmozcan1.lyricquizapp.presentation.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding, LoginViewModel>() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun layoutId() = R.layout.login_fragment

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun onViewBound() {
        binding.loginFragment = this
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setSupportActionBar(false)
        viewModel.loginViewState.observe(viewLifecycleOwner, observeViewState())
    }

    fun onLoginButtonClick(v: View) {
        viewModel.login(binding.userNameEditText.text.toString())
    }

    private fun observeViewState() = Observer<LoginViewState> { viewState ->
        when {
            viewState.hasError -> {
                makeToast(viewState.errorMessage)
            }
            viewState.isSuccess -> {
                // Login after button click
                if (viewState.isLoginSuccess) {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                // If the a user was already logged in
            }
        }
    }
}