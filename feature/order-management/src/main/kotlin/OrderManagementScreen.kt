import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.KeyboardDoubleArrowDown
import androidx.compose.material.icons.rounded.KeyboardDoubleArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.arkivanov.decompose.router.stack.pop
import core.common.navigation
import core.component_base.PostUIState

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
                            text = { Text("按订单编号排序") },
                            onClick = {
                                orderSortType = OrderSortType.Number
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
                OrderSortType.Time -> orders.sortedBy { it.orderDate }
                OrderSortType.Price -> orders.sortedBy { it.totalPrice }
                OrderSortType.Number -> orders.sortedBy { it.id }
            }.let { if (isByDesc) it.reversed() else it }
            LazyColumn {
                items(items = sortedOrders, key = {it.id}) { order ->
                    OrderItem(
                        modifier = Modifier
                            .padding(10.dp)
                            .animateItemPlacement(),
                        order = order,
                        onConfirm = { component.modelState.confirmDelivery(order.id) },
                        onDelete = { component.modelState.deleteOrder(order.id) },
                        onChangeOrder = { user, address, phone, note ->
                            component.modelState.changeOrder(order.id, user , address, phone, note)
                        }
                    )
                }
            }
        }
    }
    val deleteOrderUIStateFlow by component.modelState.deleteOrderUIStateFlow.collectAsState()
    val confirmOrderUIStateFlow by component.modelState.confirmOrderUIStateFlow.collectAsState()
    val changeOrderUIStateFlow by component.modelState.changeOrderUIStateFlow.collectAsState()
    PostUIStateDialog(postUIState = deleteOrderUIStateFlow)
    PostUIStateDialog(postUIState = confirmOrderUIStateFlow)
    PostUIStateDialog(postUIState = changeOrderUIStateFlow)
}

private enum class OrderSortType {
    Time,
    Price,
    Number
}

