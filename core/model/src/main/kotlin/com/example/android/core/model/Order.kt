package com.example.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val coupons: List<Coupon>,
    val discountedPrice: Double,
    val id: Int,
    val isDeleted: Boolean,
    val note: String,
    val orderDate: String,
    val orderNumber: String,
    val products: List<ProductAndCount>,
    val receiverAddress: String,
    val receiverName: String,
    val receiverPhone: String,
    /**
     * 1 未处理 2 已发货 3已收货 4 取消
     */
    val status: Int,
    val totalPrice: Double,
    val userId: Int
)

@Serializable
data class ProductAndCount(
    val product: Product,
    val count: Int
)