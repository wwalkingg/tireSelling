package com.example.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResp(
    val userInfo:User,
    val token:String
)
