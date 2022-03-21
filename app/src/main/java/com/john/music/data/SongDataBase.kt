package com.john.music.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.john.music.view.PopFragment

@Database(entities = [Song::class], version = 1)
abstract class SongDataBase : RoomDatabase() {

    abstract fun songDao(): SongDao

    companion object {
        @Volatile
        private var INSTANCE: SongDataBase? = null

        fun getInstance(context: PopFragment): SongDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: PopFragment)  =
            Room.databaseBuilder(context.requireContext(),
                SongDataBase::class.java,"Sample.db")
                .build()

    }
    }
