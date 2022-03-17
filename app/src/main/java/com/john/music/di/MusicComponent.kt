package com.john.music.di

import com.john.music.MainActivity
import com.john.music.presenter.RockPresenter
import com.john.music.view.PopFragment
import com.john.music.view.RockFragment
import dagger.Component

@Component(
    modules = [
        ApplicationModule::class,
        NetWorkModule::class,
        PresenterModule::class
    ]
)

interface MusicComponent {
    fun inject(rockFragment: RockFragment)
    fun inject(popFragment: PopFragment)
}