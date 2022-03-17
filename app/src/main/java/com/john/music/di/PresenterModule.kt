package com.john.music.di

import com.john.music.presenter.RockPresenter
import com.john.music.res.NetworkUtils
import com.john.music.res.TracksAPI
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class PresenterModule {

    @Provides
    fun providesRockPresenter(
        disposable: CompositeDisposable,
        networkUtils: NetworkUtils,
        tracksAPI: TracksAPI
    ):
            RockPresenter = RockPresenter(disposable,networkUtils,tracksAPI)
}