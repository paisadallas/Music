package com.john.music.di

import com.john.music.MainActivity
import com.john.music.presenter.RockPresenter
import com.john.music.view.ClassicFragment
import com.john.music.view.PopFragment
import com.john.music.view.RockFragment
import dagger.Component

/**
 * Declaring the modules for be inject
 */
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
    fun inject(classicFragment: ClassicFragment)
}