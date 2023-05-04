package com.example.android.core.model.param

import com.example.android.core.model.Coupon
import com.example.android.core.model.Product
import com.example.android.core.model.ProductAndCount
import kotlinx.serialization.Serializable

@Serializable
data class OrderParam (
    val coupons: List<Coupon>? = null,
    val note: String,
    val products: List<ProductAndCount>,
    val receiverAddress: String,
    val receiverName: String,
    val receiverPhone: String
)