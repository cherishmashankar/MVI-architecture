package com.example.android.mvi.api

import androidx.lifecycle.LiveData
import com.example.android.mvi.model.Blogs
import com.example.android.mvi.model.User
import com.example.android.mvi.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): LiveData<GenericApiResponse<User>>

    @GET("placeholder/blogs")
    fun getBlogPosts(
    ): LiveData<GenericApiResponse<List<Blogs>>>
}