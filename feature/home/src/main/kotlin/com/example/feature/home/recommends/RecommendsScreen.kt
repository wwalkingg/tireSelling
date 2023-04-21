package com.example.feature.home.recommends

import SmallLoadUIStateScaffold
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
import com.example.android.core.model.Category
import com.example.android.core.model.Product
import core.common.NavConfig
import core.common.navigation
import core.component_base.LoadUIState
import kotlinx.collections.immutable.toPersistentList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendsScreen(component: RecommendsComponent, onCategoryClick: (id: Int) -> Unit) {
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
            val loadSwiperDataUIState by component.modelState.loadSwiperDataUIStateFlow.collectAsState()
            SmallLoadUIStateScaffold(loadSwiperDataUIState) { porducts ->
                Swiper(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.background)
                        .height(100.dp)
                        .fillMaxWidth(),
                    list = porducts.map {
                        SwiperData(imgUrl = it.image) {
                            navigation.push(NavConfig.ProductDetail(it.id))
                        }
                    }.toPersistentList(),
                )
            }
            val loadHotCategoriesUIState by component.modelState.loadHotCategoriesUIStateFlow.collectAsState()
            SmallLoadUIStateScaffold(loadHotCategoriesUIState) {
                HotCategoriesContainer(
                    modifier = Modifier,
                    categories = (loadHotCategoriesUIState as LoadUIState.Loaded<List<Category>>).data.toPersistentList(),
                    onCategoryClick = { onCategoryClick(it.id) },
                )
            }

            val loadHotArticlesUIState by component.modelState.loadHotArticlesUIStateFlow.collectAsState()
            SmallLoadUIStateScaffold(
                loadHotArticlesUIState, modifier = Modifier
                    .padding(10.dp)
            ) { articles ->
                HotArticlesContainer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp),
                    articles = articles.toPersistentList(),
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