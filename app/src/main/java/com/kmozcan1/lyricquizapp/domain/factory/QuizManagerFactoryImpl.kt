package com.kmozcan1.lyricquizapp.domain.factory

import com.kmozcan1.lyricquizapp.domain.enumeration.QuizDifficulty
import com.kmozcan1.lyricquizapp.domain.manager.QuizManager
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
class QuizManagerFactoryImpl @Inject constructor() :
    QuizManagerFactory {
    override fun createQuizWith(difficulty: QuizDifficulty): QuizManager {
        return QuizManager(difficulty)
    }
}