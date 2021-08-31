package com.example.android.mvi.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


///object makes it a singleton
object RetrofitBuilder {

    const val BASE_URL = "open-api.xyz/"
    //by lazy means only once it will be instanstiated
    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: ApiService by lazy{
        retrofitBuilder.build()
            .create(ApiService::class.java)
    }
}