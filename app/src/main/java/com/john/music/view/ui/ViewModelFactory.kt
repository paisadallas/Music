package com.john.music.view.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.john.music.data.SongDao
import java.lang.IllegalArgumentException

class ViewModelFactory (private val dataSource:SongDao):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SongViewModel::class.java)){
            return SongViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Error data")
    }
}