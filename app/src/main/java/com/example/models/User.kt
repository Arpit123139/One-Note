package com.example.models

import com.google.gson.annotations.SerializedName

data class User(
    //val __v: Int,
    @SerializedName("_id")
    val userId: String,
    //val createdAt: String,
    val email: String,
    val name: String,
    //val password: String
)