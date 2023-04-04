package com.example.feature.home.recommends

import ModelState
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Category
import httpClient
import io.ktor.client.request.get
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecommendsComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext {
    internal val modelState = RecommendsModelState()
}

internal class RecommendsModelState : ModelState() {
    private val _loadHotCategoriesUIStateFlow =
        MutableStateFlow<LoadUIState<List<Category>>>(LoadUIState.Loading)
    val loadHotCategoriesUIStateFlow = _loadHotCategoriesUIStateFlow.asStateFlow()

    fun loadCategories() {
        coroutineScope.launch {
            _loadHotCategoriesUIStateFlow.emit(LoadUIState.Loading)
            httpClient.get("/categories")
        }

    }

}


sealed interface LoadUIState<out T> {
    object Loading : LoadUIState<Nothing>
    data class Loaded<out T>(val data: T) : LoadUIState<T>
    data class Error(val error: Throwable) : LoadUIState<Nothing>
}