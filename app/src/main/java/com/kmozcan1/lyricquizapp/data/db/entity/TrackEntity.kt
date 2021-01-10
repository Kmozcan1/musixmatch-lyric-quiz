package com.kmozcan1.lyricquizapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.kmozcan1.lyricquizapp.data.db.entity.TrackEntity.Companion.ID

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
@Entity (tableName = TrackEntity.TABLE_NAME, primaryKeys = [ID])
data class TrackEntity(
    @ColumnInfo(name = ID)
    var trackId: Int,
    @ColumnInfo(name = ARTIST_ID)
    var artistId: Int,
    @ColumnInfo(name = ARTIST_NAME)
    var artistName: String
){
    companion object{
        const val TABLE_NAME="tracks"
        const val ID = "id"
        const val ARTIST_ID ="artistId"
        const val ARTIST_NAME = "artistName"
    }
}