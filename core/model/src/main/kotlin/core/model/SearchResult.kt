package core.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val coaches: List<Coach>,
    val courses: List<Course>,
    val partners: List<PartnerSimple>,
    val records: List<Record>
) {
    companion object {
        val empty = SearchResult(emptyList(), emptyList(), emptyList(), emptyList())
    }
}