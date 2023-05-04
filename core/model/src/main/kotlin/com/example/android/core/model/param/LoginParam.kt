package com.example.android.core.model.param

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class LoginParam(
    val username: String,
    val password: String,
    @Required val type: Int = 2
)
