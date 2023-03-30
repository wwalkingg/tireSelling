package com.example.feature.home.category

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun CategoryScreen(modifier: Modifier = Modifier, component: CategoryComponent) {
    Scaffold(modifier, topBar = {
        TopAppBar(title = { Text(text = "全部分类") })
    }) { padding ->
        Row(
            Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .clip(RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp))
                    .width(60.dp)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                component.modelState.categorySorts.forEach {
                    val isSelected = it.id == component.modelState.selectedCategorySort
                    if (isSelected) {
                        stickyHeader(key = it.id) {
                            CategorySortItem(
                                modifier = Modifier.fillMaxSize(),
                                selected = true,
                                title = it.name,
                                onClick = { component.modelState.selectedCategorySort = it.id })
                        }
                    } else {
                        item(key = it.id) {
                            CategorySortItem(
                                modifier = Modifier.fillMaxSize(),
                                selected = false,
                                title = it.name,
                                onClick = { component.modelState.selectedCategorySort = it.id })
                        }
                    }
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .padding(PaddingValues(10.dp, 0.dp, 0.dp, 10.dp))
                    .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp))
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                items(items = component.modelState.shownProducts, key = { it.id }) {
                    CategoryItem(
                        modifier = Modifier
                            .height(80.dp)
                            .padding(4.dp), product = it,
                        onClick = {}
                    )
                }
            }
        }
    }
}