package com.example.feature.home.recommends

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.android.core.model.Article
import com.example.android.core.model.Category
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
                            .height(140.dp),
                        articles = (loadHotArticlesUIState as LoadUIState.Loaded<List<Article>>).data.toPersistentList(),
                        onArticleClick = { },
                        onMoreClick = { },
                    )
                }

                is LoadUIState.Error -> {
                    println((loadHotArticlesUIState as LoadUIState.Error).error)
                }
            }
        }
    }
}

