package com.example.feature.home.recommends

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.stack.push
import com.example.android.core.model.Article
import com.example.android.core.model.Brand
import com.example.android.core.model.Model
import com.example.feature.home.recommends.page.ArticleComponent
import core.common.NavConfig
import core.common.navigation
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.network.api.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RecommendsComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext {
    internal val modelState = RecommendsModelState()
    val articleComponent = ArticleComponent(this.childContext("article"))
}

internal class RecommendsModelState : ModelState() {
    var selectedTabIndex by mutableStateOf(0)

    private val _loadSwiperUIStateFlow = MutableStateFlow<LoadUIState<List<SwiperData>>>(LoadUIState.Loading)
    val loadSwiperUIStateFlow = _loadSwiperUIStateFlow.asStateFlow()
    private val _loadHotBrandsUIStateFlow = MutableStateFlow<LoadUIState<List<Brand>>>(LoadUIState.Loading)
    val loadHotBrandsUIStateFlow = _loadHotBrandsUIStateFlow.asStateFlow()
    private val _loadHotModelsUIStateFlow = MutableStateFlow<LoadUIState<List<Model>>>(LoadUIState.Loading)
    val loadHotModelsUIStateFlow = _loadHotModelsUIStateFlow.asStateFlow()
    private val _loadArticleUIStateFlow = MutableStateFlow<LoadUIState<List<Article>>>(LoadUIState.Loading)
    val loadArticleUIStateFlow = _loadArticleUIStateFlow.asStateFlow()

    fun loadSwiper() {
        coroutineScope.launch {
            Apis.Product.getSwiper()
                .onStart { _loadSwiperUIStateFlow.emit(LoadUIState.Loading) }
                .catch {
                    it.printStackTrace()
                    _loadSwiperUIStateFlow.emit(LoadUIState.Error(it))
                }
                .collect {
                    _loadSwiperUIStateFlow.emit(LoadUIState.Success(it.map {
                        SwiperData(
                            it.image,
                            onClick = { navigation.push(NavConfig.ProductDetail(it.id)) })
                    }))
                }
        }
    }

    fun loadHotBrands() {
        coroutineScope.launch {
            Apis.Brand.getHotBrands()
                .onStart { _loadHotBrandsUIStateFlow.emit(LoadUIState.Loading) }
                .catch {
                    it.printStackTrace()
                    _loadHotBrandsUIStateFlow.emit(LoadUIState.Error(it))
                }
                .collect {
                    _loadHotBrandsUIStateFlow.emit(LoadUIState.Success(it))
                }
        }
    }

    fun loadHotModels() {
        coroutineScope.launch {
            Apis.Model.getHotModels()
                .onStart { _loadHotModelsUIStateFlow.emit(LoadUIState.Loading) }
                .catch {
                    it.printStackTrace()
                    _loadHotModelsUIStateFlow.emit(LoadUIState.Error(it))
                }
                .collect {
                    _loadHotModelsUIStateFlow.emit(LoadUIState.Success(it))
                }
        }
    }

    fun loadArticles() {
        coroutineScope.launch {
            Apis.Article.getAllArticle()
                .onStart { _loadArticleUIStateFlow.emit(LoadUIState.Loading) }
                .catch {
                    it.printStackTrace()
                    _loadArticleUIStateFlow.emit(LoadUIState.Error(it))
                }
                .collect {
                    _loadArticleUIStateFlow.emit(LoadUIState.Success(it))
                }
        }
    }

    init {
        loadSwiper()
        loadHotBrands()
        loadHotModels()
        loadArticles()
    }
}