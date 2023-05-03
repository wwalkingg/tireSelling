package com.example.feature.home.recommends.page

import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Article
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.network.api.Apis
import core.network.api.getAllArticle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ArticleComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = ArticleModelState()
}

internal class ArticleModelState : ModelState() {

    private val _loadArticleUIStateFlow = MutableStateFlow<LoadUIState<List<Article>>>(LoadUIState.Loading)
    val loadArticleUIStateFlow = _loadArticleUIStateFlow.asStateFlow()

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
        loadArticles()
    }
}