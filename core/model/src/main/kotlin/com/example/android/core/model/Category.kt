package com.example.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val name: String,
    val description: String,
    val isDeleted: Boolean,
    val categoryImg: String
)