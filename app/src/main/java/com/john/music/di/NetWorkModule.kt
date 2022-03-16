package com.john.music.di

import com.google.gson.Gson
import dagger.Provides

class NetWorkModule {

    @Provides
    fun providesGson(): Gson = Gson()
}