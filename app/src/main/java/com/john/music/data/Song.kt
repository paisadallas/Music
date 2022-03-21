package com.john.music.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track_table")
data class Song (
    @PrimaryKey
    @ColumnInfo (name = "collection_id")
    val collectionId:Int,
    @ColumnInfo (name = "collection_name")
    val collectionName: String,
    @ColumnInfo (name = "song_name")
    val songName:String,
    @ColumnInfo (name = "song_price")
    val songPrice:String,
    @ColumnInfo (name = "song_image")
    val songImage:String,
    @ColumnInfo (name = "gender")
    val gender:String)