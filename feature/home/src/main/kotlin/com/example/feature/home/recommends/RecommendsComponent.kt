package com.example.feature.home.recommends

import ModelState
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.ArticleOnlyTitle
import com.example.android.core.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecommendsComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext {
    internal val modelState = RecommendsModelState()
}

internal class RecommendsModelState : ModelState() {
    private val _loadHotProductUIStateFlow =
        MutableStateFlow<LoadHotProductUIState>(LoadHotProductUIState.Loading)
    val loadHotProductUIStateFlow = _loadHotProductUIStateFlow.asStateFlow()

    private val _loadRecommendArticlesUIStateFlow =
        MutableStateFlow<LoadRecommendArticlesUIState>(LoadRecommendArticlesUIState.Loading)
    val loadRecommendArticlesUIStateFlow = _loadRecommendArticlesUIStateFlow.asStateFlow()


}

internal sealed interface LoadHotProductUIState {
    object Loading : LoadHotProductUIState
    data class Loaded(val hotProduct: List<Product>) : LoadHotProductUIState
    data class Error(val error: Throwable) : LoadHotProductUIState
}

internal sealed interface LoadRecommendArticlesUIState {
    object Loading : LoadRecommendArticlesUIState
    data class Loaded(val recommendArticles: List<ArticleOnlyTitle>) : LoadRecommendArticlesUIState
    data class Error(val error: Throwable) : LoadRecommendArticlesUIState
}

internal sealed interface LoadRecommendProductsUIState{
    object Loading : LoadRecommendProductsUIState
    data class Loaded(val recommendProducts: List<Product>) : LoadRecommendProductsUIState
    data class Error(val error: Throwable) : LoadRecommendProductsUIState
}