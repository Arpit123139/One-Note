package com.example.models

data class UserResponse(
    val success: Boolean,
    val token: String,
    val user: User
)