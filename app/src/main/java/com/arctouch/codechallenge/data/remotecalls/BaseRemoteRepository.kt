package com.arctouch.codechallenge.data.remotecalls

import com.arctouch.codechallenge.util.AppConstants.URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseRemoteRepository {
    protected val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()!!
}