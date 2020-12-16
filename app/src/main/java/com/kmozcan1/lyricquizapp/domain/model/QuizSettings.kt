package com.kmozcan1.lyricquizapp.domain.model

import com.kmozcan1.lyricquizapp.domain.enum.Genre
import com.kmozcan1.lyricquizapp.domain.enum.NumberOfQuestions

/**
 * Created by Kadir Mert Özcan on 14-Dec-20.
 */
data class QuizSettings(val numberOfQuestions: NumberOfQuestions, val genre: Genre) {
    companion object {
        val default = QuizSettings(NumberOfQuestions.TEN, Genre.MIXED)
    }
}

