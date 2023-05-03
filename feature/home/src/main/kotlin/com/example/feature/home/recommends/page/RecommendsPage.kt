package com.example.feature.home.recommends.page

import LoadUIStateScaffold
import SmallLoadUIStateScaffold
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arkivanov.decompose.router.stack.push
import com.example.feature.home.recommends.RecommendsComponent
import com.example.feature.home.recommends.Swiper
import com.example.feature.home.recommends.SwiperData
import components.ArticleSum
import core.common.Config
import core.common.NavConfig
import core.common.navigation
import kotlinx.collections.immutable.toPersistentList

@Composable
fun RecommendsPage(modifier: Modifier = Modifier, component: RecommendsComponent) {
    val swiperUIState by component.modelState.loadSwiperUIStateFlow.collectAsState()
    val hotBrandsUIState by component.modelState.loadHotBrandsUIStateFlow.collectAsState()
    val hotModelsUIState by component.modelState.loadHotModelsUIStateFlow.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SmallLoadUIStateScaffold(swiperUIState) { swiperDataList ->
            Swiper(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(MaterialTheme.shapes.small)
                    .fillMaxWidth()
                    .height(210.dp),
                list = swiperDataList.map { SwiperData(imgUrl = it.imgUrl, onClick = { it.onClick() }) }
                    .toPersistentList()
            )
        }

        SmallLoadUIStateScaffold(hotBrandsUIState, modifier = Modifier.padding(10.dp)) { brands ->
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(10.dp)
            ) {
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimaryContainer) {
                    Text("热门品牌")
                    Spacer(Modifier.height(10.dp))
                    LazyVerticalGrid(
                        modifier = Modifier.height(100.dp),
                        columns = GridCells.Fixed(5),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(items = brands) { brand ->
                            Column(modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    navigation.push(NavConfig.BrandDetail(brand.id))
                                }) {
                                AsyncImage(
                                    model = Config.baseImgUrl + brand,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                        .background(MaterialTheme.colorScheme.surface),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    brand.brandName,
                                    style = MaterialTheme.typography.labelMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }

        SmallLoadUIStateScaffold(hotModelsUIState, modifier = Modifier.padding(10.dp)) { models ->
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(10.dp)
            ) {
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimaryContainer) {
                    Text("热门型号")
                    Spacer(Modifier.height(10.dp))
                    LazyVerticalGrid(
                        modifier = Modifier.height(100.dp),
                        columns = GridCells.Fixed(5),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(items = models) { model ->
                            Column(modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    navigation.push(NavConfig.ModelDetail(model.id))
                                }) {
                                AsyncImage(
                                    model = Config.baseImgUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                        .background(MaterialTheme.colorScheme.surface),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    model.modelName,
                                    style = MaterialTheme.typography.labelMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
        val loadArticleUIState by component.modelState.loadArticleUIStateFlow.collectAsState()
        LoadUIStateScaffold(loadUIState = loadArticleUIState) { articles ->
            Column {
                articles.forEach { article ->
                    ArticleSum(modifier = Modifier.fillMaxWidth(), article = article) {
                        navigation.push(NavConfig.ArticleDetail(article.id))
                    }
                }
            }
        }
        Text(text = "没有更多了~", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    }
}