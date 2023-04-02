package com.example.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ArticleOnlyTitle(
    val id:Int,
    val title:String
)
