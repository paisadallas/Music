package com.john.music.presenter

import com.john.music.model.Track
import com.john.music.model.TracksItem
import com.john.music.res.NetworkUtils
import com.john.music.res.TracksAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PopPresenter @Inject constructor(
    private var disposable: CompositeDisposable,
    private val networkUtils: NetworkUtils,
    private val tracksApi: TracksAPI
) : PopPresenterContract {

    var viewContract: PopViewContract? = null

    override fun getPop() {
        viewContract?.popLoading(true)

        networkUtils.netWorkState
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { network ->
                    if (network){
                        doNetworkCall()
                    }
                    else{
                        viewContract?.popError(Throwable("Error internet connection"))
                    }

                },
                {
                   viewContract?.popError(it)
                }
            ).apply { disposable.add(this) }
    }

    private fun doNetworkCall() {
        tracksApi.getPopTracks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val track = TracksItem(50, it.tracks)
                    for (i in 0..49) {
                        viewContract?.popSuccessfull(track.tracks[i])
                    }

                },
                {
                    viewContract?.popError(it)
                }
            ).apply {
                disposable.add(this)
            }
    }

    override fun checkNetwork() {
        networkUtils.registerForNetworkState()
    }

    override fun onDestroy() {
        networkUtils.unRegisterForNetworkState()
        viewContract = null
        disposable.dispose()
    }
}

interface PopViewContract {
    fun popLoading(isLoading: Boolean)
    fun popSuccessfull(track: Track)
    fun popError(throwable: Throwable)
}

interface PopPresenterContract {
    fun getPop()
    fun checkNetwork()
    fun onDestroy()
}