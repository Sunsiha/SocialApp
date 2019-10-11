package com.moonlyte.socialapp.features.account.repository

import com.moonlyte.socialapp.features.account.model.Users
import retrofit2.Call
import retrofit2.http.GET

interface UsersAPI {

    @GET("/users")
    fun getUsers(): Call<List<Users>>
}