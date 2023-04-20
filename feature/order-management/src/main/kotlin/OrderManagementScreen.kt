import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.pop
import com.example.android.core.model.Product
import core.common.navigation
import kotlinx.datetime.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun OrderManagementScreen(modifier: Modifier = Modifier, component: OrderManagementComponent) {
    var orderSortType by remember { mutableStateOf(OrderSortType.Time) }
    var isByDesc by remember { mutableStateOf(true) }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier,
                title = { Text("订单管理") },
                navigationIcon = {
                    IconButton(onClick = { navigation.pop() }) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                }, actions = {
                    var isOrderMenuVisible by remember { mutableStateOf(false) }
                    IconButton(onClick = { isOrderMenuVisible = true }) {
                        Icon(Icons.Rounded.FilterList, contentDescription = null)
                    }
                    IconButton(onClick = { isByDesc = !isByDesc }) {
                        Icon(
                            imageVector = if (isByDesc) Icons.Rounded.KeyboardDoubleArrowDown else Icons.Rounded.KeyboardDoubleArrowUp,
                            contentDescription = null
                        )
                    }
                    DropdownMenu(expanded = isOrderMenuVisible, onDismissRequest = { isOrderMenuVisible = false }) {
                        DropdownMenuItem(
                            text = { Text("按时间排序") },
                            onClick = {
                                orderSortType = OrderSortType.Time
                                isOrderMenuVisible = false
                            })
                        DropdownMenuItem(
                            text = { Text("按名字排序") },
                            onClick = {
                                orderSortType = OrderSortType.Name
                                isOrderMenuVisible = false
                            })
                        DropdownMenuItem(
                            text = { Text("按价格排序") },
                            onClick = {
                                orderSortType = OrderSortType.Price
                                isOrderMenuVisible = false
                            })
                    }
                }
            )
        }) { padding ->
        val loadOrdersUIStateFlow by component.modelState.loadOrdersUIStateFlow.collectAsState()
        LoadUIStateScaffold(
            loadOrdersUIStateFlow,
            modifier = Modifier.padding(padding),
            onReload = { component.modelState.loadOrders() }) { orders ->
            val sortedOrders = when (orderSortType) {
                OrderSortType.Time -> orders.sortedBy { it.createTime }
                OrderSortType.Price -> orders.sortedBy { it.price }
                OrderSortType.Name -> orders.sortedBy { it.name }
            }.let { if(isByDesc) it.reversed() else it }
            LazyColumn {
                items(items = sortedOrders, key = { it.id }) { order ->
                    OrderItem(modifier = Modifier.padding(10.dp).animateItemPlacement(), order = order)
                }
            }
        }
    }
}

private enum class OrderSortType {
    Time,
    Price,
    Name
}