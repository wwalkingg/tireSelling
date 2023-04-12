package com.example.android.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val id: Int,
    val username: String?,
    val password: String?,
    val name: String?,
    @SerialName("phone_number")
    val phoneNumber: String?,
    val address: String?,
    val email: String?,
    val avatar: String?,
    @SerialName("register_time")
    val registerTime: String?,
    @SerialName("last_login_time")
    val lastLoginTime: String?,
    @SerialName("is_deleted")
    val isDeleted: Boolean = false
)

