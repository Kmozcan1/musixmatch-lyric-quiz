package com.kmozcan1.lyricquizapp.domain.factory

import com.kmozcan1.lyricquizapp.domain.enumeration.QuizDifficulty
import com.kmozcan1.lyricquizapp.domain.manager.QuizManager

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
interface QuizManagerFactory {
    fun createQuizWith(difficulty: QuizDifficulty): QuizManager
}