package com.example.android.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Coupon(
    val cashback: Double,
    val discount0: Double,
    val expiryDate: String,
    val id: Int,
    val miniAmount: Double,
    val name: String,
    val startDate: String,
    val storeId: Int,
    val type: Int,
    val usageInstructions: String
) {
    val discount = (discount0 * 100).toInt()
}

@Serializable
data class CouponPackage(
    val coupons: List<Coupon>,
    val describe: String,
    val id: Int,
    val name: String,
)
