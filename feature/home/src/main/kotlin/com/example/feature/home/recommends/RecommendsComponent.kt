package com.example.feature.home.recommends

import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Article
import com.example.android.core.model.Category
import com.example.android.core.model.Product
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.network.api.Apis
import core.network.api.getAllCategories
import core.network.api.getHotArticles
import core.network.api.getHotProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RecommendsComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext {
    internal val modelState = RecommendsModelState()
}

internal class RecommendsModelState : ModelState() {
    private val _loadHotCategoriesUIStateFlow =
        MutableStateFlow<LoadUIState<List<Category>>>(LoadUIState.Loading)
    val loadHotCategoriesUIStateFlow = _loadHotCategoriesUIStateFlow.asStateFlow()

    private val _loadHotArticlesUIStateFlow =
        MutableStateFlow<LoadUIState<List<Article>>>(LoadUIState.Loading)
    val loadHotArticlesUIStateFlow = _loadHotArticlesUIStateFlow.asStateFlow()

    private val _loadHotProductsUIStateFlow =
        MutableStateFlow<LoadUIState<List<Product>>>(LoadUIState.Loading)
    val loadHotProductsUIStateFlow = _loadHotProductsUIStateFlow.asStateFlow()

    init {
        loadHotCategories()
        loadHotArticles()
        loadHotProducts()
    }

    fun loadHotCategories() {
        coroutineScope.launch {
            Apis.Category.getAllCategories()
                .onStart { _loadHotCategoriesUIStateFlow.emit(LoadUIState.Loading) }
                .catch { _loadHotCategoriesUIStateFlow.emit(LoadUIState.Error(it)) }
                .collect { _loadHotCategoriesUIStateFlow.emit(LoadUIState.Loaded(it)) }
        }
    }

    fun loadHotArticles() {
        coroutineScope.launch {
            Apis.Article.getHotArticles()
                .onStart { _loadHotArticlesUIStateFlow.emit(LoadUIState.Loading) }
                .catch {
                    _loadHotArticlesUIStateFlow.emit(LoadUIState.Error(it))
                    it.printStackTrace()
                }
                .collect { _loadHotArticlesUIStateFlow.emit(LoadUIState.Loaded(it)) }
        }
    }

    fun loadHotProducts() {
        coroutineScope.launch {
            Apis.Product.getHotProducts()
                .onStart { _loadHotProductsUIStateFlow.emit(LoadUIState.Loading) }
                .catch { _loadHotProductsUIStateFlow.emit(LoadUIState.Error(it)) }
                .collect { _loadHotProductsUIStateFlow.emit(LoadUIState.Loaded(it)) }
        }
    }

}