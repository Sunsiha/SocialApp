package com.moonlyte.socialapp.features.post.repository

import com.moonlyte.socialapp.features.post.model.Posts
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsAPI {

    @GET("/posts?")
    fun getUserPosts(@Query("userId") userId: Int?): Call<List<Posts>>


}
