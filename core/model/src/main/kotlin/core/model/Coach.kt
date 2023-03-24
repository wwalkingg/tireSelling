package core.model

import kotlinx.serialization.Serializable

@Serializable
data class Coach(
    val id: Int,
    val age: Int,
    val sex: Int,
    val name: String,
    val avatar: String,
    val telephone: String,
    val introduce: String,
    val workYear: Int
)