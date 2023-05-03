package com.example.feature.home.category.rank

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arkivanov.decompose.router.stack.push
import com.example.android.core.model.Product
import com.example.core.design_system.Images
import core.common.Config
import core.common.NavConfig
import core.common.navigation
import kotlinx.collections.immutable.PersistentList
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalTextApi::class)
@Composable
fun RankPage(products: PersistentList<Product>) {
    LazyColumn(modifier = Modifier) {
        item {
            Box {
                Image(painter = painterResource(id = Images.rankTitle), contentDescription = null)
                Text(
                    text = "更新时间：${
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()
                    }",
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp)
                )
            }
        }
        itemsIndexed(products) { index, product ->
            val backgroundColor = if (index % 2 == 0) Color.Gray.copy(0.1f) else Color.White
            val style = if (index <= 10) MaterialTheme.typography.titleMedium.copy(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xffFF0000), Color(0xffFFDD00))
                )
            ) else MaterialTheme.typography.titleMedium
            Row(
                Modifier
                    .background(backgroundColor)
                    .clickable { navigation.push(NavConfig.ProductDetail(product.id)) }) {
                Text(
                    text = index.toString(),
                    modifier = Modifier.padding(10.dp),
                    style = style
                )
                RankItem(
                    modifier = Modifier, product = product
                )
            }
        }
    }
}

@Composable
fun RankItem(modifier: Modifier = Modifier, product: Product) {
    val mergeModifier = modifier.padding(10.dp)
    Column(mergeModifier) {
        Row(modifier = Modifier.height(IntrinsicSize.Max)) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = product.productDescription,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "${product.brand} - ${product.model}", maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            AsyncImage(
                model = Config.baseImgUrl + product.image, contentDescription = null, modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .background(Color.Gray.copy(.5f))
            )
        }
    }
}


@Preview(device = "spec:width=400dp,height=300dp,dpi=440")
@Composable
fun RankItemPreview() {
    val product = Product(
        brand = "Nike",
        discountId = 1234,
        id = 5678,
        image = "https://example.com/nike-shoes.jpg",
        isDeleted = false,
        model = "Air Max 90",
        name = "Nike Air Max 90",
        price = 109.99,
        productDescription = "The Nike Air Max 90 is a classic shoe that combines style and comfort. It features a visible Air-Sole unit in the heel for cushioning and impact protection.",
        productNumber = "NAM90-001",
        storeId = 9876,
        modelId = 11,
        brandId = 11
    )
    RankItem(product = product)
}