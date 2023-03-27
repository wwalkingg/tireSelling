package core.model

import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    val times: Int,
    val consumeTimes: Int
)