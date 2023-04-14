package core.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResp(
    val token: String
)
