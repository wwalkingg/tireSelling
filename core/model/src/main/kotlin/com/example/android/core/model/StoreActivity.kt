package com.example.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class StoreActivity(
    val id: Int,
    val storeId: Int,
    val productId: Int,
    val title: String,
    val content: String,
    val startTime: String,
    val endTime: String
)
