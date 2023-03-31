package com.example.feature.home.recommends

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.feature.home.category.CategoryItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendsScreen(modifier: Modifier = Modifier, component: RecommendsComponent) {
    Scaffold(
        topBar = {
            RecommendsTopBar()
        }) { padding ->
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
                list = List(3) { SwiperData("") },
            )
            HotInfoTitle()
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.onPrimary)
                    .padding(10.dp),
                columns = GridCells.Fixed(5),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(items = component.modelState.hotProduct) {
                    CategoryItem(
                        modifier = Modifier.height(70.dp),
                        product = it,
                        onClick = {},
                        contentPadding = PaddingValues(2.dp)
                    )
                }
            }

            SkillBlock(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            )
        }
    }
}

