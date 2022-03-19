package com.john.music.di

import com.google.gson.Gson
import com.john.music.res.NetworkInterceptor
import com.john.music.res.TracksAPI
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Provides of API methods
 * Checking internet connection
 * Time request
 */
@Module
class NetWorkModule {

    @Provides
    fun providesGson(): Gson = Gson()

    @Provides
    fun providesLogginInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun providesRequestInterceptor() : NetworkInterceptor = NetworkInterceptor()

    @Provides
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        networkInterceptor: NetworkInterceptor,
    ):OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(networkInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .build()

    @Provides
    fun providesTracksServices(gson: Gson,okHttpClient: OkHttpClient): TracksAPI =
        Retrofit.Builder()
            .baseUrl(TracksAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(TracksAPI::class.java)



}