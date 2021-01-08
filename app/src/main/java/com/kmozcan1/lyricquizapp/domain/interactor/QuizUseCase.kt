package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.enumeration.Country
import com.kmozcan1.lyricquizapp.domain.enumeration.QuizDifficulty
import com.kmozcan1.lyricquizapp.domain.factory.QuizManagerFactory
import com.kmozcan1.lyricquizapp.domain.interactor.base.ObservableUseCase
import com.kmozcan1.lyricquizapp.domain.manager.QuizManager
import com.kmozcan1.lyricquizapp.domain.model.Answer
import com.kmozcan1.lyricquizapp.domain.model.LyricsDomainModel
import com.kmozcan1.lyricquizapp.domain.model.Quiz
import com.kmozcan1.lyricquizapp.domain.model.QuizResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.SingleSubject
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 17-Dec-20.
 */

class QuizUseCase @Inject constructor(
    private val getTracksFromDatabaseUseCase: GetTracksFromDatabaseUseCase,
    private val getLyricsUseCase: GetLyricsUseCase,
    private val quizManagerFactory: QuizManagerFactory
) : ObservableUseCase<QuizResponse, QuizUseCase.Params>() {
    data class Params(
        val country: Country = Country.US,
        val quizDifficulty: QuizDifficulty = QuizDifficulty.DEFAULT
    )

    private lateinit var quizManager: QuizManager
    private lateinit var quizSubject: PublishSubject<QuizResponse>

    override fun buildObservable(params: Params?): Observable<QuizResponse> {
        // Create the publish subject that will emit results
        quizSubject = PublishSubject.create()
        // Create quiz manager with difficulty options
        quizManager = quizManagerFactory.createQuizWith(params!!.quizDifficulty)

        return quizSubject
    }

    fun generateQuiz() {
        // Fetched lyrics will be added to this list
        val lyricsList = mutableListOf<LyricsDomainModel>()

        // First, get tracks from database
        getTracksFromDatabaseUseCase.buildObservable().toFlowable().flatMap { tracks ->
            // Then, select random tracks and fetch their lyrics
            val trackMap = quizManager.selectTracks((tracks))
            getLyricsUseCase.buildObservable(
                GetLyricsUseCase.Params(trackMap)
            ).doOnNext { lyrics ->
                // Add each fetched lyrics to the lyrics list
                lyricsList.add(lyrics)
            }.doOnComplete{
                // Finally, finish generating the quest by choosing random options for each question
                quizManager.generateQuiz(trackMap, lyricsList)
                val timeLimit = quizManager.getTimeLimit()
                quizSubject.onNext(QuizResponse.quizGenerated(timeLimit))
            }
        }.subscribe()
    }

    fun startQuiz() {
        quizManager.startQuiz()
        askQuestion()
    }

    fun askQuestion() {
        quizSubject.onNext(quizManager.getCurrentQuestion())
    }

    fun validateAnswer(selectedArtistId: Int?, remainingTime: Int) {
        val answer = quizManager.validateAnswer(selectedArtistId, remainingTime)
        quizSubject.onNext(QuizResponse.answer(answer))
    }
}