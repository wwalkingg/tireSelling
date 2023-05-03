package com.example.feature.home.recommends

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.push
import com.example.feature.home.recommends.page.ArticlePage
import com.example.feature.home.recommends.page.RecommendsPage
import core.common.NavConfig
import core.common.navigation

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RecommendsScreen(component: RecommendsComponent, onCategoryClick: (id: Int) -> Unit) {
    Scaffold(topBar = {
        RecommendsTopBar(
            Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
            onSearchClick = { navigation.push(NavConfig.SearchResult) },
            selectedTabIndex = component.modelState.selectedTabIndex,
            onTabRowSelected = { component.modelState.selectedTabIndex = it })
    }) {
        val pagerState = remember { PagerState(component.modelState.selectedTabIndex) }
        LaunchedEffect(component.modelState.selectedTabIndex) {
            if (component.modelState.selectedTabIndex != pagerState.currentPage)
                pagerState.animateScrollToPage(component.modelState.selectedTabIndex)
        }
        HorizontalPager(
            pageCount = 2,
            modifier = Modifier.padding(it),
            state = pagerState,
            userScrollEnabled = false
        ) {
            when (it) {
                0 -> RecommendsPage(modifier = Modifier.fillMaxSize(), component)
                1 -> ArticlePage(component = component.articleComponent)
            }
        }
    }
}