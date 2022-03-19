package com.john.music.res

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Checking the API connection
 *
 * NO OPERATIONAL
 *
 */

class NetworkInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
     val request = chain.request().newBuilder().apply {

         addHeader("HEADER1","FirtsHeader")
         addHeader("HEADER2","SeconHeader")
         addHeader("HEADER3","SecondHeader")
     }.build()


        Log.d("HEADERS", request.header("HEADER").toString())
        return chain.proceed(request)
    }
}