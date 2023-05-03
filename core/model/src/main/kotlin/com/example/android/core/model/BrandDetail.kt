package com.example.android.core.model

import kotlinx.serialization.Serializable


@Serializable
data class BrandDetail(
    val brand: Brand,
    val products: List<Product>
)
