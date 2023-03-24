package core.model

import kotlinx.serialization.Serializable

@Serializable
data class Record(
    val id: Int,
    val userId: Int,
    val courseId: Int,
    val content: String,
    val createTime: String
)
