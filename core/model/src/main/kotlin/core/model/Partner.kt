package core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Partner(
    val id: Int,
    val name: String,
    val avatar: String,
    val password: String?,
    val isPartner: Boolean,
    val registerTime: String,
    val age: Int?,
    val sex: Int?,
    val height: Int?,
    val weight: Double?
)

@Serializable
data class PartnerSimple(
    val id: Int,
    val name: String,
    val avatar: String,
    val createTime: String?
)