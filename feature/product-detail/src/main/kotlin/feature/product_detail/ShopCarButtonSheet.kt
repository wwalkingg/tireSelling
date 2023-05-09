package feature.product_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.android.core.model.Address
import com.example.android.core.model.Coupon
import com.example.android.core.model.Product
import core.common.Config
import core.datastore.ShopCar
import kotlinx.collections.immutable.toPersistentList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopCarButtonSheet(
    onDismissRequest: () -> Unit,
    onBuy: (List<Pair<Product, Int>>, Address, coupon: Coupon?) -> Unit,
) {
    var shopCar by remember { mutableStateOf(ShopCar.retrieve(), neverEqualPolicy()) }
    var isSumVisible by remember {
        mutableStateOf(false)
    }
    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color.White)
                .padding(horizontal = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("购物车", style = MaterialTheme.typography.displaySmall)
                    Text("共商品${shopCar.productList.size}件", style = MaterialTheme.typography.labelMedium)
                }
                Button(onClick = {
                    isSumVisible = true
                }) {
                    Text("预约到店")
                }
            }
            Divider(modifier = Modifier.padding(vertical = 4.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(bottom = 10.dp)) {
                items(items = shopCar.productList) { product ->
                    Row(modifier = Modifier.height(IntrinsicSize.Max)) {
                        AsyncImage(
                            model = Config.baseImgUrl + product.first.image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                                .fillMaxHeight()
                                .aspectRatio(1f)
                                .background(Color.Gray.copy(.5f))
                        )
                        Spacer(Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(6f)) {
                            Text(product.first.name, style = MaterialTheme.typography.titleMedium)
                            Text(
                                "${product.first.brand} / ${product.first.model}",
                                style = MaterialTheme.typography.labelMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                "￥ ${String.format("%.2f", product.first.price)}",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.error,
                                fontStyle = FontStyle.Italic
                            )
                        }
                        StepNumber(modifier = Modifier, product.second, onNumberChange = {
                            val productIndex: Int = shopCar.productList.indexOfFirst { it.first.id == product.first.id }
                            println(productIndex)
                            if (productIndex != -1) {
                                shopCar.productList[productIndex] = Pair(product.first, it)
                            }
                            println(shopCar.productList[productIndex])
                            shopCar = shopCar.copy()
                            shopCar.store()
                        })
                    }
                }
            }
        }
    }

    ProductSum(
        isVisible = isSumVisible,
        onDismissRequest = { isSumVisible = false },
        onBuy = onBuy,
        productAndNumbers = shopCar.productList.toPersistentList()
    )
}

@Composable
fun StepNumber(modifier: Modifier, number: Int, onNumberChange: (Int) -> Unit) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { onNumberChange(number - 1) }) {
            Icon(Icons.Filled.Remove, contentDescription = null)
        }
        Text(number.toString())
        IconButton(onClick = { onNumberChange(number + 1) }) {
            Icon(Icons.Default.Add, contentDescription = null)
        }
    }
}