package com.john.music.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.john.music.res.NetworkUtils
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Injecting methods for Network Connection
 */

@Module
class ApplicationModule(
    private val applicationContext: Context
) {

    @Provides
    fun providesApplicationContext(): Context = applicationContext

    @Provides
    fun provideDisposible(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideNetWorkUtil(
        connectivityManager: ConnectivityManager,
        networkRequest: NetworkRequest
    ): NetworkUtils {
        return  NetworkUtils(connectivityManager,networkRequest)
    }
//hello
    @Provides
    fun providesConnectionManager(applicationContext: Context): ConnectivityManager =
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    fun provideNetWorkRequest(): NetworkRequest =
        NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

}