package com.example.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.feature.home.category.CategoryScreen
import com.example.feature.home.me.MeScreen
import com.example.feature.home.recommends.RecommendsScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, component: HomeComponent) {
    Scaffold(bottomBar = {
        HomeBottomBar(
            Modifier.fillMaxWidth(),
            component.modelState.selected,
            onSelected = { component.modelState.selected = it })
    }) { padding ->
        Column(Modifier.padding(padding)) {
            HorizontalPager(
                pageCount = BottomMenus.values().size,
                state = component.modelState.pagerState,
                userScrollEnabled = false
            ) {
                when (it) {
                    0 -> RecommendsScreen(component = component.recommendsComponent)
                    1 -> CategoryScreen(component = component.categoryComponent)
                    2 -> MeScreen(component = component.meComponent)
                }
            }
        }
    }
}