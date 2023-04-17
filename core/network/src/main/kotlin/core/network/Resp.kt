package core.network

import kotlinx.serialization.Serializable

@Serializable
data class Resp<T>(
    val code: Int,
    val msg: String,
    val data: T
)
