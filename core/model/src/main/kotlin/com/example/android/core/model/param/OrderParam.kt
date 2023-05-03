package com.example.android.core.model.param

import com.example.android.core.model.Coupon
import com.example.android.core.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class OrderParam (
    val coupon: List<Coupon>? = null,
    val note: String,
    val products: List<Product>,
    val receiverAddress: String,
    val receiverName: String,
    val receiverPhone: String
)