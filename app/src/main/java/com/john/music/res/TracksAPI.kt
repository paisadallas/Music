package com.john.music.res

import com.john.music.model.TracksItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface TracksAPI {
    //https://itunes.apple.com/search?term=rock&amp;media=music&amp;entity=song&amp;limit=50

    @GET("search?term=rock&amp;media=music&amp;entity=song&amp;limit=50")
    fun getTracks(): Call<TracksItem>

    companion object{
       const val  BASE_URL = "https://itunes.apple.com/"
       var  retrofitServices =
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(TracksAPI::class.java)
    }
}