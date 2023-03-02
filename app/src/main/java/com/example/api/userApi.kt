package com.example.api

import com.example.models.UserResponse
import com.example.models.userRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface  userApi {

    @POST("user/signup")
    suspend fun signUp(@Body userRequest:userRequest):Response<UserResponse>

    @POST("user/signIn")
    suspend fun signIn(@Body userRequest:userRequest):Response<UserResponse>
}