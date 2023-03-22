package core.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResp(
    val userInfo: UserInfo,
    val token: String
)
