package com.example.feature.home.recommends.page

import LoadUIStateScaffold
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.router.stack.push
import components.ArticleSum
import core.common.NavConfig
import core.common.navigation

@Composable
fun ArticlePage(component: ArticleComponent) {
    val loadArticleUIState by component.modelState.loadArticleUIStateFlow.collectAsState()
    LoadUIStateScaffold(loadUIState = loadArticleUIState) { articles ->
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = articles) { article ->
                ArticleSum(modifier = Modifier.fillMaxWidth(), article = article) {
                    navigation.push(NavConfig.ArticleDetail(article.id))
                }
                Divider()
            }
        }
    }
}

