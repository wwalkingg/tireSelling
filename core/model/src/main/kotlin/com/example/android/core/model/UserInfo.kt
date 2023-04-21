package com.example.android.core.model

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

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
    @Required val isDeleted: Boolean = false
)

