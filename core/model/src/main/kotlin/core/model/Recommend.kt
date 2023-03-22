package core.model

import kotlinx.serialization.Serializable

@Serializable
data class Recommend(
    val coaches: List<Coach>,
    val courses: List<Course>,
    val partners: List<Partner>
)
