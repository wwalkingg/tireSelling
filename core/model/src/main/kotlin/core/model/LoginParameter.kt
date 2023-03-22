package core.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginParameter(
    val id: String,
    val password: String
)
