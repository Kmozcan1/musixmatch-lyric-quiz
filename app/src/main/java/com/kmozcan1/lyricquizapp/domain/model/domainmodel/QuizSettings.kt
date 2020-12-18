package com.kmozcan1.lyricquizapp.domain.model.domainmodel

import com.kmozcan1.lyricquizapp.domain.enumeration.Genre
import com.kmozcan1.lyricquizapp.domain.enumeration.NumberOfQuestions

/**
 * Created by Kadir Mert Özcan on 14-Dec-20.
 */
data class QuizSettings(val numberOfQuestions: NumberOfQuestions, val genre: Genre) {
    companion object {
        val default = QuizSettings(NumberOfQuestions.TEN, Genre.MIXED)
    }
}

