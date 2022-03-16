package com.john.music.res

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


class NetworkUtils @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val networkRequest: NetworkRequest
):ConnectivityManager.NetworkCallback() {

    var netWorkState:BehaviorSubject<Boolean> = BehaviorSubject.createDefault(isNetworkAvailable())

    private fun isNetworkAvailable(): Boolean {
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let {
            return  it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
        return false
    }

    fun registerForNetworkState(){
        Log.d("MY_NETWORK","Disconnected_again")
        connectivityManager.registerNetworkCallback(networkRequest,this)
    }

    fun unRegisterForNetworkState(){
        Log.d("MY_NETWORK","Disconnected_again")
        connectivityManager.unregisterNetworkCallback(this)

    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        netWorkState.onNext(true)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        netWorkState.onNext(false)
    }
}
