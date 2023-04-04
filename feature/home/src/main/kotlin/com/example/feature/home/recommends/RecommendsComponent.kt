package com.example.feature.home.recommends

import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Article
import com.example.android.core.model.Category
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.network.api.getAllCategories
import core.network.api.getHotArticles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RecommendsComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext {
    internal val modelState = RecommendsModelState()

    init {
        println("reload")
    }
}

internal class RecommendsModelState : ModelState() {
    private val _loadHotCategoriesUIStateFlow =
        MutableStateFlow<LoadUIState<List<Category>>>(LoadUIState.Loading)
    val loadHotCategoriesUIStateFlow = _loadHotCategoriesUIStateFlow.asStateFlow()

    private val _loadHotArticlesUIStateFlow =
        MutableStateFlow<LoadUIState<List<Article>>>(LoadUIState.Loading)
    val loadHotArticlesUIStateFlow = _loadHotArticlesUIStateFlow.asStateFlow()

    init {
        loadHotCategories()
        loadHotArticles()
    }

    fun loadHotCategories() {
        coroutineScope.launch {
            getAllCategories()
                .onStart { _loadHotCategoriesUIStateFlow.emit(LoadUIState.Loading) }
                .catch { _loadHotCategoriesUIStateFlow.emit(LoadUIState.Error(it)) }
                .collect { _loadHotCategoriesUIStateFlow.emit(LoadUIState.Loaded(it)) }
        }
    }

    fun loadHotArticles() {
        coroutineScope.launch {
            getHotArticles()
                .onStart { _loadHotArticlesUIStateFlow.emit(LoadUIState.Loading) }
                .catch { _loadHotArticlesUIStateFlow.emit(LoadUIState.Error(it)) }
                .collect { _loadHotArticlesUIStateFlow.emit(LoadUIState.Loaded(it)) }
        }
    }

}