import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Order
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.network.api.Apis
import core.network.api.getOrders
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class OrderManagementComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = OrderManagementModelState()
}

internal class OrderManagementModelState : ModelState() {
    private val _loadOrdersUIStateFlow = MutableStateFlow<LoadUIState<List<Order>>>(LoadUIState.Loading)
    val loadOrdersUIStateFlow = _loadOrdersUIStateFlow.asStateFlow()

    init {
        loadOrders()
    }

    fun loadOrders() {
        coroutineScope.launch {
            Apis.Order.getOrders()
                .onStart { _loadOrdersUIStateFlow.value = LoadUIState.Loading }
                .catch { _loadOrdersUIStateFlow.value = LoadUIState.Error(it) }
                .collect { _loadOrdersUIStateFlow.value = LoadUIState.Success(it) }
        }
    }
}
