package com.example.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class User (
    val address: String,
    val email: String,
    val id: Int,
    val isDeleted: Boolean,
    val password: String? = null,
    val phoneNumber: String,
    val type: Long,
    val username: String
)

