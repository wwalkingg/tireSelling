package core.model

import kotlinx.serialization.Serializable

@Serializable
data class Statistics(
    val courses: Map<String, StatisticsCourse>,
    val records: Int,
    val tags: Map<String, Tag>,
    val useDay: Int
)

@Serializable
data class StatisticsCourse(
    val times: Int,
    val consumeEnergy: Int,
    val consumeTimes: Int
)