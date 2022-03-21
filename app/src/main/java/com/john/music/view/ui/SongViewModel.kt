package com.john.music.view.ui

import androidx.lifecycle.ViewModel
import com.john.music.data.Song
import com.john.music.data.SongDao
import io.reactivex.Completable
import io.reactivex.Flowable

class SongViewModel(private val dataSource: SongDao) : ViewModel() {

    fun collectionId(id :Int): Flowable<Song> {
        return dataSource.getSongById(id)
            .map { it
            }
    }

    fun allCollection():Flowable<Song>{
        return dataSource.getAll()
    }

    fun updateCollection(song:Song): Completable{
        val song = Song(song.collectionId,song.collectionName,song.songName,song.songPrice,song.songImage,song.gender)
        return dataSource.insertSong(song)
    }
}