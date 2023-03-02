package com.example.User

import com.example.api.userApi
import com.example.models.UserResponse
import com.example.models.userRequest
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    val userApi:userApi
)
{
    suspend fun signUp(userRequest: userRequest):Response<UserResponse>{
        return userApi.signUp(userRequest)
    }

    suspend fun signIn(userRequest: userRequest):Response<UserResponse>{
        return userApi.signIn(userRequest)
    }
}