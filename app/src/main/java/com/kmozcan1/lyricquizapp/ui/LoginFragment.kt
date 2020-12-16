package com.kmozcan1.lyricquizapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.databinding.LoginFragmentBinding
import com.kmozcan1.lyricquizapp.presentation.LoginViewModel
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
    }

    fun onLoginButtonClick(v: View) {
        viewModel.updateCurrentUser(binding.userNameEditText.text.toString())
        viewModel.loginViewState.observe(this, {
            it?.let { viewState ->
                if (viewState.hasError) {
                    Toast.makeText(this.activity,
                        viewState.errorMessage,
                        Toast.LENGTH_LONG)
                        .show()
                } else {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
            }
        })

    }
}