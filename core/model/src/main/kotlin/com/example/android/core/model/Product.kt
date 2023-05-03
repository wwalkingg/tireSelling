package com.example.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val brand: String,
    val discountId: Int,
    val id: Int,
    val image: String,
    val isDeleted: Boolean,
    val model: String,
    val modelId: Int,
    val name: String,
    val price: Double,
    val productDescription: String,
    val productNumber: String,
    val storeId: Int,
    val brandId: Int,
)

@Serializable
data class ProductsDetail(
    val product: Product,
    val store: Store,
    val comments: List<Comment>
)

@Serializable
data class Comment(
    val commentTime: String,
    val content: String,
    val id: Int,
    val productId: Int,
    val userId: Int
)
