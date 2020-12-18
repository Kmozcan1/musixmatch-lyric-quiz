package com.kmozcan1.lyricquizapp.data

import android.content.Context
import android.content.SharedPreferences
import com.kmozcan1.lyricquizapp.domain.datasource.SharedPreferencesDataSource
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.QuizResult
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.User
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 14-Dec-20.
 *
 * Data source that provides for SharedPreferences objects.
 * No business logic here, only fetches or updates the data.
 */

class SharedPreferencesDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
) : SharedPreferencesDataSource {

    companion object {
        // Pref keys
        private const val CURRENT_USER = "currentUser"
    }

    private val currentUserData: SharedPreferences by lazy {
        context.getSharedPreferences(CURRENT_USER, Context.MODE_PRIVATE)
    }

    private val userListData: SharedPreferences by lazy {
        context.getSharedPreferences("userList", Context.MODE_PRIVATE)
    }

    private val leaderBoardData: SharedPreferences by lazy {
        context.getSharedPreferences("leaderBoard", Context.MODE_PRIVATE)
    }

    // Updates the
    override fun updateCurrentUser(userName: String) {
        currentUserData.edit().apply {
            putString(CURRENT_USER, userName)
            apply()
        }
        /*objectMapper.writeValueAsString(user).also {
            userListDataEditor.putString(user.name, it)
            userListDataEditor.putString(CURRENT_USER, it)
        }
        userListDataEditor.apply()*/
    }

    override fun getCurrentUser(): User {
        TODO("Not yet implemented")
    }

    override fun addQuizResult(quizResult: QuizResult) {
        TODO("Not yet implemented")
    }

    override fun getLeaderBoard(): List<QuizResult> {
        TODO("Not yet implemented")
    }
}