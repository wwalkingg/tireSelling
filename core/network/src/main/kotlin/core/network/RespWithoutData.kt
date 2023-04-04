package core.network

import kotlinx.serialization.Serializable

@Serializable
internal data class RespWithoutData(
    val code: Int,
    val msg: String
)
