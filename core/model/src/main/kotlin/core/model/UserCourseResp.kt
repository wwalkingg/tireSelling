package core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCourseResp(
    @SerialName("isSubscriped")
    val isSubscribed: Boolean,
    val isPlaned: Boolean,
    val course: Course
)
