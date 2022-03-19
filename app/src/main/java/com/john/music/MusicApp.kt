package com.john.music

import android.app.Application
import com.john.music.di.ApplicationModule
import com.john.music.di.DaggerMusicComponent
import com.john.music.di.MusicComponent

/**
 * Injecting for MainActivity
 */

class MusicApp:Application(){

    override fun onCreate() {
        super.onCreate()

        musicComponent = DaggerMusicComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    companion object{
        lateinit var musicComponent: MusicComponent
    }
}