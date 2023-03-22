package core.network.utils

import kotlinx.serialization.Serializable

@Serializable
data class ResponseWrapper<T>(
    val code: Int,
    val data: T,
    val msg: String
)

@Serializable
data class ResponseTestWrapper(
    val code: Int,
    val msg: String,
)