package com.kmozcan1.lyricquizapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
@Entity(tableName = ScoreEntity.TABLE_NAME)
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = USER_NAME)
    var userName: String,
    @ColumnInfo(name = SCORE)
    var score: Int? = null
){
    companion object{
        const val TABLE_NAME = "scores"
        const val USER_NAME = "userName"
        const val SCORE = "score"
    }
}