package com.example.feature.home.category

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun CategoryScreen(modifier: Modifier = Modifier, component: CategoryComponent) {
//    Scaffold(modifier, topBar = {
//        TopAppBar(title = { Text(text = "全部分类") })
//    }) { padding ->
//        Row(
//            Modifier
//                .padding(padding)
//                .fillMaxSize()
//        ) {
//            LazyColumn(
//                modifier = Modifier
//                    .padding(bottom = 10.dp)
//                    .clip(RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp))
//                    .width(60.dp)
//                    .fillMaxHeight()
//                    .background(MaterialTheme.colorScheme.primaryContainer)
//            ) {
//                component.modelState.categorySorts.forEach {
//                    val isSelected = it.id == component.modelState.selectedCategorySort
//                    if (isSelected) {
//                        stickyHeader(key = it.id) {
//                            CategorySortItem(
//                                modifier = Modifier.fillMaxSize(),
//                                selected = true,
//                                title = it.name,
//                                onClick = { component.modelState.selectedCategorySort = it.id })
//                        }
//                    } else {
//                        item(key = it.id) {
//                            CategorySortItem(
//                                modifier = Modifier.fillMaxSize(),
//                                selected = false,
//                                title = it.name,
//                                onClick = { component.modelState.selectedCategorySort = it.id })
//                        }
//                    }
//                }
//            }
//        }
//    }
}