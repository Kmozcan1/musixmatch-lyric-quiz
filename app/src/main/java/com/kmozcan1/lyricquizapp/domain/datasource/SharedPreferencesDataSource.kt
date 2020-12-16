
package com.kmozcan1.lyricquizapp.domain.datasource

import com.kmozcan1.lyricquizapp.domain.model.QuizResult
import com.kmozcan1.lyricquizapp.domain.model.User

/**
 * Created by Kadir Mert Özcan on 14-Dec-20.
 */

interface SharedPreferencesDataSource {
    fun updateCurrentUser(userName: String)
    fun getCurrentUser(): User
    fun addQuizResult(quizResult: QuizResult)
    fun getLeaderBoard(): List<QuizResult>
}