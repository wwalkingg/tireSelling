package feature.all_articles

import NavigationTopBar
import SmallLoadUIStateScaffold
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import components.Coupon
import core.datastore.ShopCar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllArticlesScreen(modifier: Modifier = Modifier, component: AllArticlesComponent) {
    Scaffold(
        topBar = { NavigationTopBar(title = "预约到店") },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {}) {
                Text("确定")
            }
        }
    ) {
        Column(modifier = Modifier.padding(10.dp).padding(it)) {
            val shopCar by remember { mutableStateOf(ShopCar.retrieve()) }
            var originPrice by remember { mutableStateOf(0f) }
            LaunchedEffect(Unit) {
                originPrice = shopCar.productList.sumOf { it.first.price * it.second }.toFloat()
            }
            Row {
                Column {
                    Text("共商品${shopCar.productList.size}件", style = MaterialTheme.typography.displaySmall)
                    component.modelState.selectedCoupon?.let {
                        Text("已选择优惠券", style = MaterialTheme.typography.titleMedium)
                        Coupon(modifier = Modifier, coupon = it, onClick = {})
                    }
                    Row {
                        var isSelectCouponDialogVisible by remember { mutableStateOf(false) }
                        Button(onClick = { isSelectCouponDialogVisible = true }) {
                            Text("选择优惠券")
                        }
                        if (isSelectCouponDialogVisible) {
                            Dialog(onDismissRequest = { isSelectCouponDialogVisible = false }) {
                                Column(
                                    modifier = Modifier.clip(MaterialTheme.shapes.large)
                                        .background(MaterialTheme.colorScheme.surface)
                                        .padding(10.dp)
                                ) {
                                    Text("我的优惠券", style = MaterialTheme.typography.displaySmall)

                                    val loadCouponsUIState by component.modelState.loadCouponsUIStateFlow.collectAsState()
                                    SmallLoadUIStateScaffold(
                                        loadCouponsUIState,
                                        modifier = Modifier.heightIn(min = 500.dp).padding(10.dp)
                                    ) { coupons ->
                                        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                            coupons.forEach { coupon ->
                                                item {
                                                    Coupon(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        coupon = coupon,
                                                        onClick = { component.modelState.selectedCoupon = coupon })
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Text(
                        "共 ￥$originPrice",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.displaySmall
                    )
                    component.modelState.selectedCoupon?.let { coupon ->
                        if (coupon.type == 1) {
                            Text("已经优惠 ￥${coupon.cashback}", style = MaterialTheme.typography.displaySmall)
                        } else {
                            Text("已经优惠 ￥${coupon.discount * originPrice}", style = MaterialTheme.typography.displaySmall)
                        }
                    }
                }
            }
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(items = shopCar.productList) { product ->
                    Row(modifier = Modifier.height(IntrinsicSize.Max)) {
                        AsyncImage(
                            model = "",
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.clip(MaterialTheme.shapes.medium).fillMaxHeight().aspectRatio(1f)
                                .background(MaterialTheme.colorScheme.surface)
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
                    }
                }
            }
        }
    }
}