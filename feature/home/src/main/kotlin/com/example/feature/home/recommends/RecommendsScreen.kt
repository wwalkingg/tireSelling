package com.example.feature.home.recommends

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.push
import com.example.android.core.model.Article
import com.example.android.core.model.Category
import com.example.android.core.model.Product
import core.common.NavConfig
import core.common.navigation
import core.component_base.LoadUIState
import kotlinx.collections.immutable.toPersistentList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendsScreen(component: RecommendsComponent) {
    Scaffold(topBar = { RecommendsTopBar() }) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        0f to MaterialTheme.colorScheme.primary,
                        10f to Color.Unspecified,
                        startY = 0f,
                        endY = 800.0f
                    )
                )
                .verticalScroll(rememberScrollState())
        ) {
            Swiper(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.background)
                    .height(100.dp)
                    .fillMaxWidth(),
                list = List(3) { SwiperData("") }.toPersistentList(),
            )
            val loadHotCategoriesUIState by component.modelState.loadHotCategoriesUIStateFlow.collectAsState()
            when (loadHotCategoriesUIState) {
                LoadUIState.Loading -> {
                    Text(text = "Loading")
                }

                is LoadUIState.Loaded -> {
                    HotCategoriesContainer(
                        modifier = Modifier,
                        categories = (loadHotCategoriesUIState as LoadUIState.Loaded<List<Category>>).data.toPersistentList(),
                        onCategoryClick = { },
                    )
                }

                is LoadUIState.Error -> {
                    println((loadHotCategoriesUIState as LoadUIState.Error).error)
                }
            }

            val loadHotArticlesUIState by component.modelState.loadHotArticlesUIStateFlow.collectAsState()
            when (loadHotArticlesUIState) {
                LoadUIState.Loading -> {
                    Text(text = "Loading")
                }

                is LoadUIState.Loaded -> {
                    HotArticlesContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp),
                        articles = (loadHotArticlesUIState as LoadUIState.Loaded<List<Article>>).data.toPersistentList(),
                        onArticleClick = {
                            navigation.push(
                                NavConfig.ArticleDetail(
                                    it.id,
                                    it.title
                                )
                            )
                        },
                        onMoreClick = { navigation.push(NavConfig.AllArticles) },
                    )
                }

                is LoadUIState.Error -> {
                    Text(text = (loadHotArticlesUIState as LoadUIState.Error).error.toString())
                }
            }

            val loadHotProductsUIState by component.modelState.loadHotProductsUIStateFlow.collectAsState()
            when (loadHotProductsUIState) {
                LoadUIState.Loading -> {
                    Text(text = "Loading")
                }

                is LoadUIState.Loaded -> {
                    HotProductsFlowContainer(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth(),
                        products = (loadHotProductsUIState as LoadUIState.Loaded<List<Product>>).data.toPersistentList(),
                        onProductClick = {
                            navigation.push(
                                NavConfig.ProductDetail(
                                    it.id,
                                    it.name,
                                    it.image
                                )
                            )
                        },
                    )
                }

                is LoadUIState.Error -> {
                    println((loadHotProductsUIState as LoadUIState.Error).error)
                }
            }
        }
    }
}