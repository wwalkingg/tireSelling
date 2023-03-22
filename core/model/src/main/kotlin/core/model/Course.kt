package core.model

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val id: Int,
    val type: Int,
    val title: String,
    val uploadTime: String,
    val difficulty: Int,
    val cover: String,
    val consumeEnergy: Int,
    val consumeTime: Int,
    val experienceRequirement: Int,
    val times:Int,
    val courseIntroduce:String,
    val tag:Int
)
