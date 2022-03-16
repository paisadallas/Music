package com.john.music.presenter

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.john.music.model.Track
import com.john.music.model.TracksItem
import com.john.music.res.TracksServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RockPresenter(
    private var context: Context? = null,
    private var viewContract: RockViewContract ?= null
) {

    private val connectivityManager by lazy {
        context?.getSystemService(Context.CONNECTIVITY_SERVICE) as?  ConnectivityManager
    }

    fun getRock() {
        //Loading
        viewContract?.rockLoading(true)

        if (isNetworkAvaliable()) {
            //Response
            TracksServices.retrofitServices.getTracks().enqueue(object : Callback<TracksItem> {
                override fun onResponse(call: Call<TracksItem>, response: Response<TracksItem>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val track = TracksItem(50, it.tracks)

                            for (i in 0..49) {

                                viewContract?.rockSuccessfull(track.tracks[i])

                                //  trackAdapter.updateTrack(yo.tracks[i])
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<TracksItem>, t: Throwable) {
                    Log.d("Error", "error")
                    viewContract?.rockError(t)
                }

            })
        }else{
            viewContract?.rockError(Throwable("NO NETWORK CONNECTION"))
        }
    }

    fun destroyPresent(){
        viewContract = null
    }

    private fun isNetworkAvaliable():Boolean{
        connectivityManager?.getNetworkCapabilities(connectivityManager?.activeNetwork)?.let {
            if(it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                return  true
            }
        }
        return false
    }
}

interface RockViewContract{
    fun rockLoading(isLoading: Boolean)
    fun rockSuccessfull(track: Track)
    fun rockError (throwable: Throwable)
}