package com.john.music.res

import com.john.music.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(NetworkInterceptor())
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .build()
    }

    var  retrofitServices =
        Retrofit.Builder()
            .baseUrl(TracksAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpsClient)
            .build()
            .create(TracksAPI::class.java)
}