package com.example.feature.home.category.sort

import LoadUIStateScaffold
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.push
import components.ProductItem
import core.common.NavConfig
import core.common.navigation
import kotlinx.collections.immutable.toPersistentList
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SortPage(component: SortComponent) {
    val loadSortUIState by component.modelState.loadSortUIStateFlow.collectAsState()
    LoadUIStateScaffold(loadUIState = loadSortUIState) { products ->
        var brand by remember {
            mutableStateOf("全部")
        }
        var model by remember {
            mutableStateOf("全部")
        }
        var priceFrom by remember {
            mutableStateOf(0f)
        }
        var priceTo by remember {
            mutableStateOf(1f)
        }
        var filterProducts by remember {
            mutableStateOf(products.toPersistentList())
        }
        LaunchedEffect(brand, model, priceFrom, priceTo) {
            filterProducts = products.filter {
                (brand == "全部" || it.brand == brand) &&
                        (model == "全部" || it.model == model) &&
                        ( it.price >= priceFrom * 10000) &&
                        ( it.price <= priceTo * 10000)
            }.toPersistentList()
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            stickyHeader {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp),modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Column {
                            var isBrandMenuVisible by remember {
                                mutableStateOf(false)
                            }
                            OutlinedTextField(
                                value = brand,
                                onValueChange = {},
                                label = { Text(text = "品牌") },
                                trailingIcon = {
                                    IconButton(onClick = { isBrandMenuVisible = true }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = null
                                        )
                                    }
                                },
                                readOnly = true
                            )
                            DropdownMenu(
                                expanded = isBrandMenuVisible,
                                onDismissRequest = { isBrandMenuVisible = false }
                            ) {
                                DropdownMenuItem(text = { Text("全部") }, onClick = { brand = "全部" })
                                products.map { it.brand }.toSet().forEach {
                                    DropdownMenuItem(text = { Text(it) }, onClick = { brand = it })
                                }
                            }
                        }
                        Column {
                            var isBrandMenuVisible by remember {
                                mutableStateOf(false)
                            }
                            OutlinedTextField(
                                value = model,
                                onValueChange = {},
                                label = { Text(text = "型号") },
                                trailingIcon = {
                                    IconButton(onClick = { isBrandMenuVisible = true }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = null
                                        )
                                    }
                                },
                                readOnly = true
                            )
                            DropdownMenu(
                                expanded = isBrandMenuVisible,
                                onDismissRequest = { isBrandMenuVisible = false },
                            ) {
                                DropdownMenuItem(text = { Text("全部") }, onClick = { model = "全部" })
                                products.map { it.model }.toSet().forEach {
                                    DropdownMenuItem(text = { Text(it) }, onClick = { model = it })
                                }
                            }
                        }
                        Column {
                            Text(text = "价格区间", style = MaterialTheme.typography.titleMedium)
                            RangeSlider(
                                value = priceFrom.rangeTo(priceTo),
                                onValueChange = {
                                    priceFrom = it.start
                                    priceTo = it.endInclusive
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text = "${(priceFrom * 10000).roundToInt()} - ${(priceTo * 10000).roundToInt()}",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Gray
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .background(MaterialTheme.colorScheme.outlineVariant)
                    )
                    Text(
                        text = "筛选结果",
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
            items(items = filterProducts, key = {it.id}) { product ->
                ProductItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItemPlacement()
                        .clickable { navigation.push(NavConfig.ProductDetail(product.id)) },
                    product = product
                )
                Divider()
            }
        }
    }
}