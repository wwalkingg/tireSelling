package com.example.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Model (
    val compatibleVehicles: String,
    val id: Int,
    val modelName: String,
    val modelNumber: String
)
