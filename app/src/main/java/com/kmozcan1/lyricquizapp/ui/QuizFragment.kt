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
import com.kmozcan1.lyricquizapp.databinding.QuizFragmentBinding
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.Question
import com.kmozcan1.lyricquizapp.presentation.viewmodel.LoginViewModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.QuizViewState
import com.kmozcan1.lyricquizapp.presentation.viewmodel.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : BaseFragment<QuizFragmentBinding, QuizViewModel>() {

    companion object {
        fun newInstance() = QuizFragment()
    }

    override fun layoutId() = R.layout.quiz_fragment

    override fun getViewModelClass(): Class<QuizViewModel> = QuizViewModel::class.java

    override fun onViewBound() {
        binding.quizFragment = this
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setSupportActionBar(true, getString(R.string.quiz))
        // Observe ViewState
        viewModel.quizViewState.observe(viewLifecycleOwner, viewStateObserver())
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
        when {
            viewState.hasError -> {
                makeToast(viewState.errorMessage)
                findNavController().navigateUp()
            }
            viewState.isLoading -> {
                binding.quizProgressBar.visibility = View.VISIBLE
            }
            viewState.isSuccess -> {
                binding.quizProgressBar.visibility = View.GONE
                when {
                    viewState.isScorePosted -> {
                        showScoreScreen()
                    }
                    viewState.isQuizFinished -> {
                        hideOptionView()
                    }
                }
            }
        }
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
        findNavController().navigate(R.id.action_quizFragment_to_leaderboardFragment2)
    }

    fun onHomeButtonClick(v: View) {
        findNavController().navigate(R.id.action_quizFragment_to_homeFragment)
    }

    private fun hideOptionView() {
        binding.quizOptionsView.visibility = View.GONE
    }

    private fun showScoreScreen() {
        binding.scoreScreen.visibility = View.VISIBLE
        binding.finalScoreTextView.text = getString(R.string.final_score, binding.scoreTextView.text)
        binding.quizOptionsView.isEnabled = false
        binding.quizOptionsView.visibility = View.GONE
    }
}