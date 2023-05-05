import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arkivanov.decompose.router.stack.push
import com.example.android.core.model.CarStore
import com.example.android.core.model.Order
import components.CarStore
import components.QRCodeImage
import core.common.Config
import core.common.NavConfig
import core.common.navigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderItem(
    modifier: Modifier = Modifier,
    order: Order,
    onConfirm: () -> Unit,
    onDelete: () -> Unit,
    onChangeOrder: (name: String, address: String, phone: String, note: String) -> Unit
) {
    Surface(
        modifier, shape = MaterialTheme.shapes.extraSmall
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Column(
                modifier = Modifier.height(IntrinsicSize.Max), verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row {
                    Text(
                        text = "订单编号 ${order.id}", style = MaterialTheme.typography.titleMedium, color = Color.Gray
                    )
                }
                order.products.forEach { productAndCount ->
                    val product = productAndCount.product
                    Row(
                        modifier = Modifier
                            .height(IntrinsicSize.Max)
                            .clickable { navigation.push(NavConfig.ProductDetail(product.id)) },
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        AsyncImage(
                            model = Config.baseImgUrl + product.image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.small)
                                .size(50.dp, 40.dp)
                                .background(Color.Gray)
                        )
                        Column(modifier = Modifier.fillMaxHeight()) {
                            Text(text = product.name, style = MaterialTheme.typography.bodyLarge)
                            Text(
                                text = "￥${String.format("%.2f", product.price)}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                        Text(text = "×${productAndCount.count}")
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Divider(modifier = Modifier.padding(vertical = 4.dp))
            val totalPrice0 = order.products.sumOf {
                it.product.price * it.count
            }
            val totalPrice = if (order.coupons.isEmpty()) {
                totalPrice0
            } else {
                val coupon = order.coupons[0]
                if (coupon.type == 1) {
                    totalPrice0 - coupon.cashback
                } else totalPrice0 * coupon.discount / 100
            }
            Text(
                text = "总计 ￥${String.format("%.2f", totalPrice)}",
                color = MaterialTheme.colorScheme.error,
                fontStyle = FontStyle.Italic
            )
            Divider(modifier = Modifier.padding(vertical = 4.dp))
            Text(
                text = "预约门店", style = MaterialTheme.typography.titleMedium, color = Color.Gray
            )
            val carStore = CarStore(
                name = order.receiverName,
                address = order.receiverAddress,
                phone = order.receiverPhone,
            )
            CarStore(modifier = Modifier.fillMaxWidth(),carStore = carStore)
            Divider(modifier = Modifier.padding(vertical = 4.dp))
            val coupon = order.coupons.firstOrNull()
            if (coupon != null) {
                if (coupon.type == 1) {
                    Text(text = "${String.format("%.2f", coupon.cashback)} 现金券")
                } else {
                    Text(text = "${String.format("%.2f", coupon.discount)} 折扣券")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    val statusText = "状态： " + when (order.status) {
                        1 -> "未处理"
                        2 -> "已发货"
                        3 -> "已收货"
                        4 -> "被取消"
                        else -> "未知"
                    }
                    Text(text = statusText, style = MaterialTheme.typography.bodyMedium)
                }
                when (order.status) {
                    1 -> {
                        var isShow by remember {
                            mutableStateOf(false)
                        }
                        TextButton(onClick = { isShow = true }) {
                            Text(text = "取消订单")
                        }

                        if (isShow) {
                            AlertDialog(onDismissRequest = { isShow = false }, confirmButton = {
                                Button(onClick = {
                                    isShow = false
                                    onDelete()
                                }) {
                                    Text(text = "确定取消")
                                }
                            }, dismissButton = {
                                Button(onClick = { isShow = false }) {
                                    Text(text = "取消操作")
                                }
                            }, title = {
                                Text(text = "是否取消订单")
                            })
                        }
                    }

                    2 -> {
                        var isShow by remember {
                            mutableStateOf(false)
                        }
                        TextButton(onClick = {
                            isShow = true
                        }) {
                            Text(text = "确认收货")
                        }
                        if (isShow) {
                            AlertDialog(onDismissRequest = { isShow = false }, confirmButton = {
                                Button(onClick = {
                                    isShow = false
                                    onConfirm()
                                }) {
                                    Text(text = "确定")
                                }
                            }, dismissButton = {
                                Button(onClick = { isShow = false }) {
                                    Text(text = "取消")
                                }
                            }, title = {
                                Text(text = "已确定收到货物")
                            })
                        }

                    }
                }
            }
            Row {
                var isVisible by remember {
                    mutableStateOf(false)
                }
                Button(onClick = {
                    isVisible = true
                }) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.QrCode, contentDescription = null)
                        Text(text = "到店显示二维码")
                    }
                }
                if (isVisible) {
                    AlertDialog(
                        containerColor = Color.White,
                        onDismissRequest = { isVisible = false },
                        title = {
                            Text(text = "到店预约")
                        },
                        text = {
                            QRCodeImage(content = order.id.toString())
                        },
                        confirmButton = {
                            TextButton(onClick = { isVisible = false }) {
                                Text(text = "确定")
                            }
                        }
                    )
                }
            }
        }
    }
}