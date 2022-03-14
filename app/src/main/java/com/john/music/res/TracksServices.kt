package com.john.music.res

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TracksServices {

    var  retrofitServices =
        Retrofit.Builder()
            .baseUrl(TracksAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TracksAPI::class.java)
}