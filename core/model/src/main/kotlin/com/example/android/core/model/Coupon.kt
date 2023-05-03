package com.example.android.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Coupon (
    val cashback: Int,
    val discount: Int,
    val expiryDate: String,
    val id: Int,
    val miniAmount: Int,
    val name: String,
    val startDate: String,
    val storeId: Int,
    val type: Int,
    val usageInstructions: String
)
@Serializable
data class CouponPackage (
    val coupons: List<Coupon>,
    val describe: String,
    val id: Int,
    val name: String,
    val storeId: Int,
    val type: String
)
