package com.john.music.res

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import io.reactivex.subjects.BehaviorSubject

class NetworkUtils(
    private var context: Context?=null,
    private val connectivityManager: ConnectivityManager? =
        context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager,
    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()
):ConnectivityManager.NetworkCallback() {

    var netWorkState:BehaviorSubject<Boolean> = BehaviorSubject.createDefault(isNetworkAvailable())

    private fun isNetworkAvailable(): Boolean {
        connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)?.let {
            if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                || it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) return  true
        }
        return false
    }

    fun registerForNetworkState(){
        Log.d("MY_NETWORK","Disconnected_again")
        connectivityManager?.registerNetworkCallback(networkRequest,this)
    }

    fun unRegisterForNetworkState(){
        Log.d("MY_NETWORK","Disconnected_again")
        connectivityManager?.unregisterNetworkCallback(this)
        context = null
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
