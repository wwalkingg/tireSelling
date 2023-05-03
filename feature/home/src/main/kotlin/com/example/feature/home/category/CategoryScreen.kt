package com.example.feature.home.category

import SmallLoadUIStateScaffold
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core.design_system.Images
import com.example.feature.home.category.brand.BrandPage
import com.example.feature.home.category.model.ModelPage
import com.example.feature.home.category.rank.RankPage
import com.example.feature.home.category.sort.SortPage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.collections.immutable.toPersistentList

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(component: CategoryComponent) {
    val systemController = rememberSystemUiController()
    SideEffect {
        systemController.setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
    }
    val pagerState = rememberPagerState()
    var selectedTabIndex by remember { mutableStateOf(0) }
    LaunchedEffect(key1 = selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    Scaffold(
        topBar = {
            TabRow(selectedTabIndex = selectedTabIndex) {
                Tab(selected = selectedTabIndex == 0, onClick = { selectedTabIndex = 0 }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconImage(painterResource(id = Images.rank))
                        Text(text = "排行榜")
                    }
                }
                Tab(selected = selectedTabIndex == 1, onClick = { selectedTabIndex = 1 }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconImage(painterResource(id = Images.model))
                        Text(text = "型号大全")
                    }
                }
                Tab(selected = selectedTabIndex == 2, onClick = { selectedTabIndex = 2 }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconImage(painterResource(id = Images.brand))
                        Text(text = "品牌")
                    }
                }
                Tab(selected = selectedTabIndex == 3, onClick = { selectedTabIndex = 3 }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconImage(painterResource(id = Images.sort))
                        Text(text = "筛选")
                    }
                }
            }
        }
    ) {
        HorizontalPager(
            modifier = Modifier.padding(it),
            pageCount = 4,
            userScrollEnabled = false,
            state = pagerState
        ) { page ->
            when (page) {
                0 -> {
                    val loadRankUIState by component.modelState.loadRankUIStateFlow.collectAsState()
                    SmallLoadUIStateScaffold(loadUIState = loadRankUIState) {
                        RankPage(products = it.toPersistentList())
                    }
                }

                1 -> {
                    val loadAllModels by component.modelState.loadAllModelsUIStateFlow.collectAsState()
                    SmallLoadUIStateScaffold(loadUIState = loadAllModels) { models ->
                        ModelPage(models = models.toPersistentList())
                    }
                }

                2 -> {
                    val loadAllBrands by component.modelState.loadAllBrandsUIStateFlow.collectAsState()
                    SmallLoadUIStateScaffold(loadUIState = loadAllBrands) { brands ->
                        BrandPage(brands = brands.toPersistentList())
                    }

                }

                3 -> {
                    SortPage(component = component.sortComponent)
                }
            }
        }
    }
}


@Composable
private fun IconImage(painter: Painter) {
    Image(
        painter = painter, contentDescription = null,
        Modifier
            .padding(4.dp, 14.dp)
            .size(24.dp)
    )
}