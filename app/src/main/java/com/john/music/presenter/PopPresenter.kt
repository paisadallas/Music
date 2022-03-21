package com.john.music.presenter

import androidx.fragment.app.viewModels
import com.john.music.data.Song
import com.john.music.model.Track
import com.john.music.model.TracksItem
import com.john.music.res.NetworkUtils
import com.john.music.res.TracksAPI
import com.john.music.view.ui.SongViewModel
import com.john.music.view.ui.ViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Presenter Pop music
 *
 * receiving three methods, CompositeDisposable, NetworkUtils and TracksApi
 *
 * Implementing two interfaces
 * Pop View contract : View the actual state of internet connection
 * Pop Present contract: Implementing on the fragment
 */
class PopPresenter @Inject constructor(
    private var disposable: CompositeDisposable,
    private val networkUtils: NetworkUtils,
    private val tracksApi: TracksAPI
) : PopPresenterContract {




    var viewContract: PopViewContract? = null

    var mySong = Song(1,"rice","milk","10.0","nn","pop")

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
                  //  val track = TracksItem(50, it.tracks)
//                    for (i in 0..49) {
//                        viewContract?.popSuccessfull(track.tracks[i])
//                    }
                    viewContract?.popSuccessfull(mySong)


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
    fun popSuccessfull(song: Song)
    fun popError(throwable: Throwable)
}

interface PopPresenterContract {
    fun getPop()
    fun checkNetwork()
    fun onDestroy()
}