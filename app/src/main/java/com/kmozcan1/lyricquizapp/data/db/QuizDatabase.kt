package com.kmozcan1.lyricquizapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kmozcan1.lyricquizapp.data.db.QuizDatabase.Companion.DB_VERSION
import com.kmozcan1.lyricquizapp.data.db.dao.ScoreDao
import com.kmozcan1.lyricquizapp.data.db.dao.TrackDao
import com.kmozcan1.lyricquizapp.data.db.dao.UserDao
import com.kmozcan1.lyricquizapp.data.db.entity.ScoreEntity
import com.kmozcan1.lyricquizapp.data.db.entity.TrackEntity
import com.kmozcan1.lyricquizapp.data.db.entity.UserEntity

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
@Database(entities = [TrackEntity::class, ScoreEntity::class, UserEntity::class], version = DB_VERSION)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun userDao(): UserDao
    abstract fun scoreDao(): ScoreDao

    companion object {
        const val DB_VERSION = 4

        const val DB_NAME = "musixmatchquiz.db"

        @Volatile
        private var dbInstance: QuizDatabase? = null

        fun getDatabaseInstance(mContext: Context): QuizDatabase =
            dbInstance ?: synchronized(this) {
                dbInstance ?: buildDatabaseInstance(mContext).also {
                    dbInstance = it
                }
            }

        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, QuizDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()

    }

}

