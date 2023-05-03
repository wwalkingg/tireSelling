package feature.product_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.push
import core.common.NavConfig
import core.common.navigation

@Composable
fun ProductDetailBottomBar(
    modifier: Modifier = Modifier,
    onInsertCart: () -> Unit,
    price: Double,
    onBuy: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.errorContainer)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "还有优惠券没领取！！！", color = MaterialTheme.colorScheme.error)
            TextButton(onClick = { navigation.push(NavConfig.CouponCenter)}) {
                Text(text = "点击去优惠中心领取")
            }
        }
        BottomAppBar(modifier) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onInsertCart) {
                    Text("加入购物车")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        String.format("￥ %.2f", price),
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(Modifier.width(10.dp))
                    Button(onClick = onBuy) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                painterResource(com.example.core.design_system.Icons.shoppingBag),
                                contentDescription = null,
                                modifier = Modifier.size(22.dp)
                            )
                            Text("购买")
                        }
                    }
                }
            }
        }
    }
}