package core.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val course: List<Course>
) {
    companion object {
        val empty = SearchResult(emptyList())
    }
}