package com.example.android.mvi.api

import com.example.android.mvi.model.Blogs
import com.example.android.mvi.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): User

    @GET("/placeholder/blogs")
    fun getBlogPosts(
    ): List<Blogs>
}