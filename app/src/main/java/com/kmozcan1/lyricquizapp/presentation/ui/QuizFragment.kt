package com.kmozcan1.lyricquizapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.databinding.QuizFragmentBinding
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.Question
import com.kmozcan1.lyricquizapp.presentation.viewmodel.LoginViewModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.QuizViewState
import com.kmozcan1.lyricquizapp.presentation.viewmodel.QuizViewModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.QuizViewState.State.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : BaseFragment<QuizFragmentBinding, QuizViewModel>() {

    companion object {
        fun newInstance() = QuizFragment()
    }

    override fun layoutId() = R.layout.quiz_fragment

    override fun getViewModelClass(): Class<QuizViewModel> = QuizViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            var a = 1
        }

        callback.isEnabled = true

    }

    override fun onViewBound() {
        binding.quizFragment = this
        setSupportActionBar(true, getString(R.string.quiz))
        showBottomNavigation(false)
    }

    override fun observe() {
        // Observe ViewState
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
        // Observe questions
        viewModel.questionLiveData.observe(viewLifecycleOwner, questionObserver())
        // Observe the timer
        viewModel.timerLiveData.observe(viewLifecycleOwner, timerObserver())
        // Observe the score
        viewModel.scoreLiveData.observe(viewLifecycleOwner, scoreObserver())
        // Set listener for generated buttons
        binding.quizOptionsView.setOptionButtonClickListener(optionButtonClickListener())

        viewModel.createQuiz()
    }

    private fun viewStateObserver() = Observer<QuizViewState> { viewState ->
        when (viewState.state) {
            ERROR -> {
                makeToast(viewState.errorMessage)
                navController.navigateUp()
            }
            LOADING -> {
                binding.quizProgressBar.visibility = View.VISIBLE
            }
            QUIZ_GENERATED -> {
                binding.quizProgressBar.visibility = View.GONE
            }
            SCORE_POSTED -> {
                showScoreScreen()
            }
            QUIZ_FINISHED -> {
                hideOptionView()
            }
        }
    }

    private fun showScoreScreen() {
        binding.scoreScreen.visibility = View.VISIBLE
        binding.finalScoreTextView.text = getString(R.string.final_score, binding.scoreTextView.text)
        binding.quizOptionsView.isEnabled = false
        binding.quizOptionsView.visibility = View.GONE
    }

    private fun hideOptionView() {
        binding.quizOptionsView.visibility = View.GONE
    }

    private fun questionObserver() = Observer<Question> { question ->
        if (!binding.quizOptionsView.buttonsAreSet) {
            binding.quizOptionsView.setOptionButtons(question.options)
            binding.quizOptionsView.visibility = View.VISIBLE }
        else {
            binding.quizOptionsView.renameOptionButtons(question.options)
            binding.quizOptionsView.isEnabled = true

        }
        binding.questionTextView.text = question.lyric

    }

    private fun timerObserver() = Observer<String> {  time ->
        binding.timerTextView.text = time
    }

    private fun scoreObserver() = Observer<String> { score ->
        binding.scoreTextView.text = score
    }



    private fun optionButtonClickListener() = object : OptionsView.OptionButtonClickListener {
        override fun onOptionButtonClicked(artistName: String) {
            //TODO Workaround for pressed button not being changed
            binding.quizOptionsView.isClickable = false
            viewModel.checkAnswer(artistName)
        }
    }

    fun onLeaderBoardButtonClick(v: View) {
        navController.navigate(R.id.action_quizFragment_to_leaderboardFragment2)
    }

    fun onHomeButtonClick(v: View) {
        navController.navigate(R.id.action_quizFragment_to_homeFragment)
    }

    override fun onDestroy() {
        viewModel.dispose()
        super.onDestroy()
    }
}