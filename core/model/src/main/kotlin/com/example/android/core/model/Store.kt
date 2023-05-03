package com.example.android.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Store (
    val address: String,
    val contactNumber: String,
    val id: Int,
    val isDeleted: Boolean,
    val storeName: String,
    val storeNumber: String,
    val userId: Long
)