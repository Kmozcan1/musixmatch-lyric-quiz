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
import com.kmozcan1.lyricquizapp.domain.model.viewstate.LoginViewState
import com.kmozcan1.lyricquizapp.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = LoginFragmentBinding.inflate(
            inflater, container, false
        )
        binding.loginFragment = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.checkIfLoggedIn()
        viewModel.loginViewState.observe(viewLifecycleOwner, observeViewState())
    }

    fun onLoginButtonClick(v: View) {
        viewModel.login(binding.userNameEditText.text.toString())
    }

    private fun observeViewState() = Observer<LoginViewState> { viewState ->
        if (viewState.hasError) {
                Toast.makeText(
                    this.activity,
                    viewState.errorMessage,
                    Toast.LENGTH_LONG
                )
                    .show()
            } else if (viewState.isSuccess) {
                // Login after button click
                if (viewState.isLoginSuccess) {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                // If the a user was already logged in
                else if (viewState.isLoggedIn) {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
            }
    }
}