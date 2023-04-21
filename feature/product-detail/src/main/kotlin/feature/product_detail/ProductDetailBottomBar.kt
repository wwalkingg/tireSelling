package feature.product_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeatPump
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProductDetailBottomBar(
    modifier: Modifier = Modifier,
    isCollected: Boolean,
    price: Double,
    onCollectClick: () -> Unit,
    onBuy: () -> Unit
) {
    BottomAppBar(modifier) {
        var isCollected0 by remember(isCollected) { mutableStateOf(isCollected) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {
                isCollected0 = !isCollected0
                onCollectClick()
            }) {
                val icon = if (isCollected0) {
                    painterResource(com.example.core.design_system.Icons.heatFill)
                } else painterResource(com.example.core.design_system.Icons.heat)
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    String.format("CNY %.2f", price),
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