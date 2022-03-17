package com.john.music.presenter

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.john.music.model.Track
import com.john.music.model.TracksItem
import com.john.music.res.NetworkUtils
import com.john.music.res.TracksAPI
import com.john.music.res.TracksServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RockPresenter @Inject constructor(
    private var disposable: CompositeDisposable,
    private val networkUtils: NetworkUtils,
    private val tracksApi : TracksAPI

) : RockPresenterContract {

    var viewContract: RockViewContract? = null

    override fun getRock() {
        //Loading
        viewContract?.rockLoading(true)

        networkUtils.netWorkState
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ network ->
                if (network) {
                    doNetworkCall()
                } else {
                    viewContract?.rockError(Throwable("Error internet connection"))
                }
            },
                {
                    viewContract?.rockError(it)
                }
            )
            .apply { disposable.add(this) }

    }

    private fun doNetworkCall() {
        tracksApi.getTracks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val track = TracksItem(50, it.tracks)
                    for (i in 0..49) {
                        viewContract?.rockSuccessfull(track.tracks[i])
                    }

                },
                {
                    viewContract?.rockError(it)
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

interface RockViewContract {
    fun rockLoading(isLoading: Boolean)
    fun rockSuccessfull(track: Track)
    fun rockError(throwable: Throwable)
}

interface RockPresenterContract {
    fun getRock()
    fun checkNetwork()
    fun onDestroy()
}