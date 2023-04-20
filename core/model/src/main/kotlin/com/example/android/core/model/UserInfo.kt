package com.example.android.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val id: Int,
    val username: String?,
    val password: String?,
    val name: String?,
    val phoneNumber: String?,
    val address: String?,
    val email: String?,
    val avatar: String?,
    val registerTime: String?,
    val lastLoginTime: String?,
    val isDeleted: Boolean = false
)

