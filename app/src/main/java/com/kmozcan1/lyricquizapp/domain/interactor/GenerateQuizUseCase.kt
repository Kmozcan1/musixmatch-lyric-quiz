package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.Constants.NUMBER_OF_TRACKS_TO_FETCH
import com.kmozcan1.lyricquizapp.domain.enumeration.Country
import com.kmozcan1.lyricquizapp.domain.enumeration.QuizDifficulty
import com.kmozcan1.lyricquizapp.domain.factory.QuizManagerFactory
import com.kmozcan1.lyricquizapp.domain.interactor.base.FlowableUseCase
import com.kmozcan1.lyricquizapp.domain.interactor.base.SingleUseCase
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.LyricsDomainModel
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.Quiz
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.SingleSubject
import io.reactivex.rxjava3.subjects.Subject
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 17-Dec-20.
 */

class GenerateQuizUseCase @Inject constructor(
    private val getTracksFromDatabaseUseCase: GetTracksFromDatabaseUseCase,
    private val getLyricsUseCase: GetLyricsUseCase,
    private val quizManagerFactory: QuizManagerFactory
) : SingleUseCase<Quiz, GenerateQuizUseCase.Params>() {
    data class Params(
        val country: Country = Country.US,
        val quizDifficulty: QuizDifficulty = QuizDifficulty.DEFAULT
    )

    override fun buildObservable(params: Params?): Single<Quiz> {
        val quizManager = quizManagerFactory.createQuizWith(params!!.quizDifficulty)
        val lyricsList = mutableListOf<LyricsDomainModel>()
        var quiz: Quiz?
        val quizSubject: SingleSubject<Quiz> = SingleSubject.create()
        // First, get tracks from database
        getTracksFromDatabaseUseCase.buildObservable().toFlowable().flatMap { tracks ->
            // Then, select random tracks and fetch their lyrics
            getLyricsUseCase.buildObservable(
                GetLyricsUseCase.Params(quizManager.selectTracks((tracks)))
            ).doOnNext { lyrics ->
                // Add each fetched lyrics to the lyrics list
                lyricsList.add(lyrics)
            }.doOnComplete{
                // Finally, finish generating the quest by choosing random options for each question
                quiz = quizManager.generateQuiz(lyricsList)
                quizSubject.onSuccess(quiz)
            }
        }.subscribe()
        return quizSubject
    }
}