package com.example.android.core.model

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class OrderParam(
    val storeId: Int,
    val productId: Int,
    val price: Float,
    @Required val createTime: String = "",
    val address: String
)
