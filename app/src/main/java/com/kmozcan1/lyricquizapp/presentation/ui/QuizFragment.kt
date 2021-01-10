package com.kmozcan1.lyricquizapp.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.databinding.QuizFragmentBinding
import com.kmozcan1.lyricquizapp.presentation.viewmodel.QuizViewModel
import com.kmozcan1.lyricquizapp.presentation.viewstate.QuizViewState
import com.kmozcan1.lyricquizapp.presentation.viewstate.QuizViewState.State.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuizFragment : BaseFragment<QuizFragmentBinding, QuizViewModel>() {

    companion object {
        fun newInstance() = QuizFragment()
    }

    override val layoutId = R.layout.quiz_fragment

    override val viewModelClass: Class<QuizViewModel> = QuizViewModel::class.java

    var quizFinished: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBackPressCallback()
    }

    private fun setBackPressCallback() {
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (quizFinished) {
                navController.navigate(R.id.viewPagerFragment)
            } else {
                MaterialAlertDialogBuilder(requireActivity())
                        .setTitle(resources.getString(R.string.quit_quiz))
                        .setMessage(resources.getString(R.string.quit_quiz_message))
                        .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .setPositiveButton(resources.getString(R.string.quit)) { dialog, _ ->
                            navController.navigate(R.id.viewPagerFragment)
                            dialog.dismiss()
                        }
                        .show()
            }
        }
        callback.isEnabled = true
    }

    override fun onViewBound() {
        binding.quizFragment = this
        setSupportActionBar(true)
        showBottomNavigation(false)
        setBottomNavigationNoSelectedItem()
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
        binding.run {
            when (viewState.state) {
                ERROR -> {
                    makeToast(viewState.errorMessage)
                    navController.navigateUp()
                }
                LOADING -> {
                    quizProgressBar.visibility = View.VISIBLE
                }
                QUIZ_GENERATED -> {
                    quizProgressBar.visibility = View.GONE
                    quizView.visibility = View.VISIBLE
                    scoreTextView.text = 0.toString()
                    viewModel.startQuiz()
                }
                QUESTION -> {
                    setOptions(viewState)
                    questionTextView.text = viewState.question!!.lyric
                }
                ANSWER -> {
                    viewModel.stopTimer()
                    with(viewState.answer!!) {
                        scoreTextView.text = updatedScore.toString()
                        showCorrectAnswer(selectedArtistId, correctArtistId)
                    }
                }
                SCORE_POSTED -> {
                    showScoreScreen()
                }
                QUIZ_FINISHED -> {
                    hideQuizView()
                    quizFinished = true
                }
            }
        }
    }

    private fun showCorrectAnswer(selectedArtistId: Int?, correctArtistId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.quizOptionsView.showCorrectAnswer(selectedArtistId, correctArtistId)
            delay(750)
            viewModel.askNextQuestion()
        }
    }

    private fun setOptions(viewState: QuizViewState) {
        viewState.question?.let { question ->
            with(binding.quizOptionsView) {
                // If the buttons are being set for the first time
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

    private fun hideQuizView() {
        binding.quizView.visibility = View.GONE
    }

    private fun timerObserver() = Observer<String> {  time ->
        binding.timerTextView.text = time
    }

    private fun optionButtonClickListener() = object : OptionsView.OptionButtonClickListener {
        override fun onOptionButtonClicked(artistId: Int) {
            if (binding.quizOptionsView.buttonsAreEnabled) {
                binding.quizOptionsView.buttonsAreEnabled = false
                viewModel.validateAnswer(artistId)
            }

        }
    }

    override fun onDestroy() {
        viewModel.dispose()
        super.onDestroy()
    }
}