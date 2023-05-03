import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.android.core.model.Order
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
    onChangeOrder: (name: String, address: String, phone: String,note:String) -> Unit
) {
    Surface(
        modifier,
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Column(
                modifier = Modifier
                    .height(IntrinsicSize.Max), verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row {
                    Text(
                        text = "订单编号 ${order.id}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray
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
            Divider()
            val totalPrice0 = order.products.sumOf {
                it.product.price * it.count
            }
            val totalPrice = if (order.coupons.size == 0) {
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
                            AlertDialog(
                                onDismissRequest = { isShow = false },
                                confirmButton = {
                                    Button(onClick = {
                                        isShow = false
                                        onDelete()
                                    }) {
                                        Text(text = "确定取消")
                                    }
                                },
                                dismissButton = {
                                    Button(onClick = { isShow = false }) {
                                        Text(text = "取消操作")
                                    }
                                },
                                title = {
                                    Text(text = "是否取消订单")
                                }
                            )
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
                            AlertDialog(
                                onDismissRequest = { isShow = false },
                                confirmButton = {
                                    Button(onClick = {
                                        isShow = false
                                        onConfirm()
                                    }) {
                                        Text(text = "确定")
                                    }
                                },
                                dismissButton = {
                                    Button(onClick = { isShow = false }) {
                                        Text(text = "取消")
                                    }
                                },
                                title = {
                                    Text(text = "已确定收到货物")
                                }
                            )
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
                    Text(text = "修改订单")
                }
                if (isVisible) {
                    var user by remember {
                        mutableStateOf(order.receiverName)
                    }
                    var address by remember {
                        mutableStateOf(order.receiverAddress)
                    }
                    var phone by remember {
                        mutableStateOf(order.receiverPhone)
                    }
                    var note by remember {
                        mutableStateOf(order.receiverPhone)
                    }
                    AlertDialog(
                        onDismissRequest = { isVisible = false },
                        title = { Text(text = "修改订单信息") },
                        text = {
                            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                TextField(
                                    value = user,
                                    onValueChange = { user = it },
                                    label = { Text(text = "收获联系人") })
                                TextField(
                                    value = address,
                                    onValueChange = { address = it },
                                    label = { Text(text = "收获地址") })
                                TextField(
                                    value = phone,
                                    onValueChange = { phone = it },
                                    label = { Text(text = "联系电话") })
                                TextField(
                                    value = note,
                                    onValueChange = { note = it },
                                    label = { Text(text = "备注") })
                            }
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                onChangeOrder(user, address, phone,note)
                                isVisible = false
                            }
                            ) {
                                Text(text = "确定")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { isVisible = false }) {
                                Text(text = "取消")
                            }
                        }
                    )
                }
            }
        }
    }
}