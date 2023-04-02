package com.example.android.core.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Product(
    val id: Int,
    val storeId: Int,
    val categoryId: Int,
    val name: String,
    val image: String,
    val price: Double,
    val description: String,
    val createTime: String,
    @Transient
    val isDeleted: Boolean = false
)
