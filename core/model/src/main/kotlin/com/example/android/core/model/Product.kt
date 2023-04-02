package com.example.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val name: String,
    val imgUrl: String,
    val isHot: Boolean
)
