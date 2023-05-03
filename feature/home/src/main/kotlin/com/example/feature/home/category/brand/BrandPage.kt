package com.example.feature.home.category.brand

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arkivanov.decompose.router.stack.push
import com.example.android.core.model.Brand
import core.common.Config
import core.common.NavConfig
import core.common.navigation
import kotlinx.collections.immutable.PersistentList

@Composable
fun BrandPage(brands: PersistentList<Brand>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(10.dp)
    ) {
        items(brands) { brand ->
            BrandItem(brand = brand) {
                navigation.push(NavConfig.BrandDetail(brand.id))
            }
        }
    }
}

@Composable
fun BrandItem(modifier: Modifier = Modifier, brand: Brand, onClick: () -> Unit) {
    val mergeModifier = modifier
        .clip(MaterialTheme.shapes.small)
        .clickable { onClick() }
        .padding(5.dp)
    Column(
        mergeModifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AsyncImage(
            model = Config.baseImgUrl + brand.icon,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color.Gray.copy(.4f))
        )
        Text(text = brand.brandName, style = MaterialTheme.typography.titleMedium)
    }
}

@Preview
@Composable
fun BrandItemPreview() {
    val brand = Brand(
        brandName = "Nike",
        brandNumber = "NK-001",
        country = "USA",
        id = 987654321,
        introduce = "Nike is a multinational corporation that is engaged in the design, development, manufacturing, and worldwide marketing and sales of footwear, apparel, equipment, accessories, and services.",
        icon = "https://www.nike.com/favicon.ico"
    )
    BrandItem(brand = brand, onClick = {})
}