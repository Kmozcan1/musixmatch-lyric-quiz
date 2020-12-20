package com.kmozcan1.lyricquizapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
@Entity(tableName = UserEntity.TABLE_NAME, indices = [Index(value = ["userName"], unique = true)])
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = USER_NAME)
    var userName: String? = null
){
    companion object{
        const val TABLE_NAME = "users"
        const val USER_NAME = "userName"
    }
}