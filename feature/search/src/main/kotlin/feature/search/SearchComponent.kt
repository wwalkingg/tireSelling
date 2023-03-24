package feature.search

import FilterSearchTypes
import ModelState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import core.model.SearchResult
import core.network.utils.error
import core.network.utils.success
import httpClient
import io.ktor.client.request.*
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = instanceKeeper.getOrCreate { SearchModelState() }
}

internal class SearchModelState : ModelState() {
    var searchValue by mutableStateOf("")
    var selectedTypes by mutableStateOf(emptySet<FilterSearchTypes>().toPersistentSet())
    private val _searchState = MutableStateFlow<SearchState>(SearchState.Success(SearchResult.empty))
    val searchState = _searchState.asStateFlow()
    fun search() {
        coroutineScope.launch {
            _searchState.emit(SearchState.Loading)
            httpClient.get("/search"){parameter("text",searchValue)}.success<SearchResult> {
                _searchState.emit(SearchState.Success(it))
            }.error {
                _searchState.emit(SearchState.Error)
            }
        }
    }
}

internal sealed interface SearchState {
    object Loading : SearchState
    object Error : SearchState
    data class Success(val data: SearchResult) : SearchState
}