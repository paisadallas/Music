package com.john.music.di

import android.content.Context
import com.john.music.data.SongDao
import com.john.music.data.SongDataBase
import com.john.music.view.PopFragment
import com.john.music.view.ui.ViewModelFactory

object Offline {

    fun provideSongDataSource(context:PopFragment):SongDao{
        val dataBase = SongDataBase.getInstance(context)
        return dataBase.songDao()
    }

   // fun provide

    fun provideViewModelFactory(context: PopFragment):ViewModelFactory{
        val dataSource = provideSongDataSource(context)
        return ViewModelFactory(dataSource)
    }
}