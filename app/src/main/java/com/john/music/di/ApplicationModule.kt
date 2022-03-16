package com.john.music.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule (
    private val applicationContext: Context
){

    @Provides
    fun providesApplicationContext(): Context = applicationContext
}