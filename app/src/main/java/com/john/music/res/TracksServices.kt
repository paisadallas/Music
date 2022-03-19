package com.john.music.res

import com.john.music.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Services NO OPERATIONAL
 * I don't delete it only for to know the implementation
 */
object TracksServices {

    private val httpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            if(BuildConfig.DEBUG){
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }
//Adding interceptors
    private val okHttpsClient by lazy{
        OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .build()
    }

    var  retrofitServices =
        Retrofit.Builder()
            .baseUrl(TracksAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //Reactive MODULE
            .client(okHttpsClient)
            .build()
            .create(TracksAPI::class.java)
}