package components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arkivanov.decompose.router.stack.push
import com.example.android.core.model.Product
import com.example.core.design_system.Icons
import core.common.Config
import core.common.NavConfig
import core.common.navigation
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecommendProducts(modifier: Modifier = Modifier, products: PersistentList<Product>) {
    Column(modifier.padding(10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = Icons.hotInfo), contentDescription = null, tint = Color.Red)
            Text(text = "为您推荐", style = MaterialTheme.typography.titleLarge, color = Color.Red)
        }
        BoxWithConstraints {
            val width = maxWidth
            FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                products.take(12).forEach { product ->
                    Column(
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .clip(MaterialTheme.shapes.small)
                            .clickable { navigation.push(NavConfig.ProductDetail(product.id)) }
                            .width((width - 30.dp) / 4)
                            .height(100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = Config.baseImgUrl + product.image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.small)
                                .background(Color.Gray.copy(.5f))
                                .weight(1f)
                        )
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun RecommendProductsPreview() {
    val productList = listOf(
        Product(
            brand = "Nike",
            discountId = 1234,
            id = 5678,
            image = "https://example.com/nike-shoes-1.jpg",
            isDeleted = false,
            model = "Air Max 90",
            name = "Nike Air Max 90",
            price = 109.99,
            productDescription = "The Nike Air Max 90 is a classic shoe that combines style and comfort. It features a visible Air-Sole unit in the heel for cushioning and impact protection.",
            productNumber = "NAM90-001",
            storeId = 9876,
            modelId = 11,
            brandId = 11
        ),
        Product(
            brand = "Adidas",
            discountId = 5678,
            id = 1234,
            image = "https://example.com/adidas-shoes-1.jpg",
            isDeleted = false,
            model = "Ultraboost 21",
            name = "Adidas Ultraboost 21",
            price = 179.99,
            productDescription = "The Adidas Ultraboost 21 is a high-performance running shoe that delivers exceptional comfort and energy return. It features a Boost midsole and a lightweight, breathable upper.",
            productNumber = "ADUB21-001",
            storeId = 6543,
            modelId = 11,
            brandId = 11
        ),
        Product(
            brand = "Under Armour",
            discountId = 0,
            id = 2468,
            image = "https://example.com/under-armour-shoes-1.jpg",
            isDeleted = false,
            model = "Charged Pursuit 2",
            name = "Under Armour Charged Pursuit 2",
            price = 69.99,
            productDescription = "The Under Armour Charged Pursuit 2 is a versatile running shoe that delivers comfort and performance. It features a lightweight and breathable upper and a Charged Cushioning midsole.",
            productNumber = "UACP2-001",
            storeId = 1357,
            modelId = 11,
            brandId = 11
        )
    )
    RecommendProducts(products = productList.toPersistentList())
}