package com.example.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ArticleDetail(
    val id: Int,
    val title: String,
    val content: String,
    val publishDateTime: String,
    val productTypeId: Int,
    val productTypeName: String,
    val isHot: Boolean
)
