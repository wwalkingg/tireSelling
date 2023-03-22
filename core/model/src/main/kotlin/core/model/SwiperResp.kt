package core.model

import kotlinx.serialization.Serializable

@Serializable
data class SwiperResp(
    val id: Int,
    val imageUrl: String,
    val createTime: String
)
