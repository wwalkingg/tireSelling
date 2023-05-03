package com.example.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Brand (
    val brandName: String,
    val brandNumber: String,
    val country: String,
    val id: Int,
    val introduce: String,
    val icon:String
)
