
package com.kmozcan1.lyricquizapp.domain.datasource

import com.kmozcan1.lyricquizapp.domain.model.domainmodel.QuizResult
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.User
import io.reactivex.rxjava3.core.Single

/**
 * Created by Kadir Mert Özcan on 14-Dec-20.
 */

interface SharedPreferencesDataSource {
    fun updateCurrentUser(userName: String)
    fun getCurrentUser(): Single<String>
    fun addQuizResult(quizResult: QuizResult)
    fun getLeaderBoard(): List<QuizResult>
}