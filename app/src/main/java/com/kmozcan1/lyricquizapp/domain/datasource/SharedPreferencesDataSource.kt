
package com.kmozcan1.lyricquizapp.domain.datasource

import io.reactivex.rxjava3.core.Single

/**
 * Created by Kadir Mert Özcan on 14-Dec-20.
 */

interface SharedPreferencesDataSource {
    fun updateCurrentUser(userName: String)
    fun getCurrentUser(): Single<String>
}