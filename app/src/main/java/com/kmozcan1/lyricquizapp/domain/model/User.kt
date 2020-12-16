package com.kmozcan1.lyricquizapp.domain.model

import com.kmozcan1.lyricquizapp.domain.enum.Genre
import com.kmozcan1.lyricquizapp.domain.enum.NumberOfQuestions

/**
 * Created by Kadir Mert Özcan on 14-Dec-20.
 */
data class User(
    val name: String,
    val quizSettings: QuizSettings ?= QuizSettings.default
) {

    companion object {
        private const val USERNAME_INVALID = "The user name is invalid."

    }

    init {
        // Validate user name
        require(name.isNotBlank()) {
            USERNAME_INVALID
        }
    }
}