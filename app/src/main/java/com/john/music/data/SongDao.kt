package com.john.music.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface SongDao {

    @Query("SELECT * FROM track_table WHERE collection_id= :id")
    fun getSongById(id:Int): Flowable<Song>

    @Query("SELECT * FROM track_table")
    fun getAll(): Flowable<Song>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSong(song: Song):Completable

    @Query("DELETE FROM track_table")
    suspend fun deleteAll()
}