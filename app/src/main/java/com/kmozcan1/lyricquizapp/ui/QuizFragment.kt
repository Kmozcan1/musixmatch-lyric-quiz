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
import com.kmozcan1.lyricquizapp.databinding.QuizFragmentBinding
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.Question
import com.kmozcan1.lyricquizapp.domain.model.viewstate.QuizViewState
import com.kmozcan1.lyricquizapp.presentation.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : Fragment() {

    companion object {
        fun newInstance() = QuizFragment()
    }

    private lateinit var viewModel: QuizViewModel
    private lateinit var binding: QuizFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = QuizFragmentBinding.inflate(
            inflater, container, false
        )
        binding.quizFragment = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        // Observe ViewState
        viewModel.quizViewState.observe(viewLifecycleOwner, viewStateObserver())
        // Observe questions
        viewModel.questionLiveData.observe(viewLifecycleOwner, questionObserver())
        // Observe the timer
        viewModel.timerLiveData.observe(viewLifecycleOwner, timerObserver())
        // Set listener for generated buttons
        binding.quizOptionsView.setOptionButtonClickListener(buttonClickListener())
        viewModel.createQuiz()

    }

    private fun viewStateObserver() = Observer<QuizViewState> {
        it?.let { viewState ->
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
                viewState.isLoading -> {
                    binding.quizProgressBar.visibility = View.VISIBLE
                }
                viewState.isSuccess -> {
                    binding.quizProgressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun questionObserver() = Observer<Question> {
        it?.let { question ->
            if (binding.quizOptionsView.visibility == View.GONE) {
                binding.quizOptionsView.setOptionButtons(question.options)
                binding.quizOptionsView.visibility = View.VISIBLE
            } else {
                binding.quizOptionsView.renameOptionButtons(question.options)
            }
            binding.questionTextView.text = question.lyric
        }
    }

    private fun timerObserver() = Observer<String> {
        it?.let { time ->
            binding.timerTextView.text = time
        }
    }

    private fun buttonClickListener() = object : OptionsView.OptionButtonClickListener {
        override fun onOptionButtonClicked(artistName: String) {
            viewModel.checkAnswer(artistName)
        }
    }
}