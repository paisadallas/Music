package com.john.music.presenter

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.john.music.model.Track
import com.john.music.model.TracksItem
import com.john.music.res.NetworkUtils
import com.john.music.res.TracksServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RockPresenter(
    private var context: Context? = null,
    private var viewContract: RockViewContract? = null,
    private var disposable: CompositeDisposable = CompositeDisposable(),
    private val networkUtils: NetworkUtils = NetworkUtils(context)

) : RockPresenterContract {


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
        TracksServices.retrofitServices.getTracks()
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
        context = null
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