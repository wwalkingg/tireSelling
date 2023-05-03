package com.example.android.core.model

import com.example.android.core.model.Model
import com.example.android.core.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class ModelDetail(
    val model: Model,
    val products: List<Product>
)
