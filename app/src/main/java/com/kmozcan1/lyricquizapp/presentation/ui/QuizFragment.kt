package com.kmozcan1.lyricquizapp.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.databinding.QuizFragmentBinding
import com.kmozcan1.lyricquizapp.presentation.viewstate.QuizViewState
import com.kmozcan1.lyricquizapp.presentation.viewmodel.QuizViewModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.QuizViewState.State.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        // Observe the timer
        viewModel.timerLiveData.observe(viewLifecycleOwner, timerObserver())
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
                binding.scoreTextView.text = 0.toString()
                viewModel.startQuiz()
            }
            QUESTION -> {
                setOptions(viewState)
                binding.questionTextView.text = viewState.question!!.lyric
            }
            ANSWER -> {
                viewModel.stopTimer()
                with(viewState.answer!!) {
                    binding.scoreTextView.text = updatedScore.toString()
                    showCorrectAnswer(selectedArtistId, correctArtistId)
                }
            }
            SCORE_POSTED -> {
                showScoreScreen()
            }
            QUIZ_FINISHED -> {
                hideOptionView()
            }
        }
    }

    private fun showCorrectAnswer(selectedArtistId: Int?, correctArtistId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.quizOptionsView.showCorrectAnswer(selectedArtistId, correctArtistId)
            delay(1000)
            viewModel.askNextQuestion()
        }
    }

    private fun setOptions(viewState: QuizViewState) {
        viewState.question?.let { question ->
            with(binding.quizOptionsView) {
                if (!buttonsAreSet) {
                    setOptionButtons(question.options)
                    visibility = View.VISIBLE
                } else {
                    refreshOptionButtons(question.options)
                    isEnabled = true
                }
            }
        }
    }

    private fun showScoreScreen() {
        binding.scoreScreen.visibility = View.VISIBLE
        binding.finalScoreTextView.text = getString(R.string.final_score, binding.scoreTextView.text)
        binding.quizOptionsView.isEnabled = false
        binding.quizOptionsView.visibility = View.GONE
        showBottomNavigation(true)
    }

    private fun hideOptionView() {
        binding.quizOptionsView.visibility = View.GONE
    }

    private fun timerObserver() = Observer<String> {  time ->
        binding.timerTextView.text = time
    }

    private fun optionButtonClickListener() = object : OptionsView.OptionButtonClickListener {
        override fun onOptionButtonClicked(artistId: Int) {
            binding.quizOptionsView.isClickable = false
            viewModel.validateAnswer(artistId)
        }
    }

    override fun onDestroy() {
        viewModel.dispose()
        super.onDestroy()
    }
}