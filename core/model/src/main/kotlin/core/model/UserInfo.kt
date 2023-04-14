package core.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val id: Int,
    val name: String?,
    val avatar: String?,
    val password: String?,
    val isPartner: Boolean,
    val registerTime: String?,
    val age: Int?,
    val sex: Int?,
    val height: Int?,
    val weight: Float?,
)