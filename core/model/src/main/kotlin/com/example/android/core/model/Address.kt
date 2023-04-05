package com.example.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val name: String,
    val phone: String,
    val address: String,
    val detailAddress: String
)