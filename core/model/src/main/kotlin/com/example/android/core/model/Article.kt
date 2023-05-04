package com.example.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val userId:Int?,
    val id: Int,
    val title: String,
    val content: String,
    val publishDate: String,
    val productType: String,
    val images: List<String>
)
