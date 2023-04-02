package com.example.android.core.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Store(
    val id: Int,
    val farmerId: Int,
    val name: String,
    val logo: String,
    val description: String,
    val createTime: String,
    @Transient
    val isDeleted: Boolean = false
)
