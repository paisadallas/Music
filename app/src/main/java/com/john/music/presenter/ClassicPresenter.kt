package com.john.music.presenter

import com.john.music.model.Track
import com.john.music.model.TracksItem
import com.john.music.res.NetworkUtils
import com.john.music.res.TracksAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ClassicPresenter @Inject constructor(
    private var disposable: CompositeDisposable,
    private val networkUtils: NetworkUtils,
    private val tracksApi : TracksAPI
): ClassicPresenterContract {

    var viewContract:ClassicViewContract?= null

    override fun getClassic() {
        viewContract?.classicLoading(true)

        networkUtils.netWorkState
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ network ->
                if (network) {
                    doNetworkCall()
                } else {
                    viewContract?.classicError(Throwable("Error internet connection"))
                }
            },
                {
                    viewContract?.classicError(it)
                }
            )
            .apply { disposable.add(this) }
    }

    private fun doNetworkCall() {
        tracksApi.getClassicTracks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val track = TracksItem(50, it.tracks)
                    for (i in 0..49) {
                        viewContract?.classicSuccessfull(track.tracks[i])
                    }

                },
                {
                    viewContract?.classicError(Throwable("Error internet connection"))
                }
            ).apply {
                disposable.add(this)
            }
    }

    override fun checkNetwork() {
        networkUtils.registerForNetworkState()
    }

    override fun onDestroy() {
        networkUtils.registerForNetworkState()
        viewContract = null
        disposable.dispose()
    }
}

interface ClassicViewContract {
    fun classicLoading(isLoading: Boolean)
    fun classicSuccessfull(track: Track)
    fun classicError(throwable: Throwable)
}

interface ClassicPresenterContract{
    fun getClassic()
    fun checkNetwork()
    fun onDestroy()
}