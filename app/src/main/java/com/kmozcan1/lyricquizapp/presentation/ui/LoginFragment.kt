package com.kmozcan1.lyricquizapp.presentation.ui

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
import com.kmozcan1.lyricquizapp.presentation.viewstate.LoginViewState.State.*
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
        setSupportActionBar(false)
        showBottomNavigation(false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun observe() {
        viewModel.viewState.observe(viewLifecycleOwner, observeViewState())
    }

    private fun observeViewState() = Observer<LoginViewState> { viewState ->
        when (viewState.state){
            ERROR -> {
                makeToast(viewState.errorMessage)
            }
            LOGIN -> {
                val navAction =  LoginFragmentDirections
                    .actionLoginFragmentToHomeFragment(viewState.userName!!)
                navController.navigate(navAction)
            }
            LOADING -> {

            }
        }
    }

    fun onLoginButtonClick(v: View) {
        viewModel.login(binding.userNameEditText.editText?.text.toString())
    }
}