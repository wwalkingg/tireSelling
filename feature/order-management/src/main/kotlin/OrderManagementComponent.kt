import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Order
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.component_base.PostUIState
import core.network.api.*
import kotlinx.coroutines.delay
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
            Apis.Order.getAllOrders()
                .onStart { _loadOrdersUIStateFlow.value = LoadUIState.Loading }
                .catch { _loadOrdersUIStateFlow.value = LoadUIState.Error(it) }
                .collect { _loadOrdersUIStateFlow.value = LoadUIState.Success(it) }
        }
    }

    private val _deleteOrderUIStateFlow = MutableStateFlow<PostUIState>(PostUIState.None)
    val deleteOrderUIStateFlow = _deleteOrderUIStateFlow.asStateFlow()


    fun deleteOrder(id: Int) {
        coroutineScope.launch {
            Apis.Order.deleteOrder(id)
                .onStart {
                    _deleteOrderUIStateFlow.emit(PostUIState.Loading)
                }
                .catch {
                    _deleteOrderUIStateFlow.emit(PostUIState.Error(it))
                    delay(2000L)
                    it.printStackTrace()
                    _deleteOrderUIStateFlow.emit(PostUIState.None)
                }
                .collect {
                    _deleteOrderUIStateFlow.emit(PostUIState.Success)
                    delay(2000L)
                    _deleteOrderUIStateFlow.emit(PostUIState.None)
                }
        }
    }

    private val _confirmOrderUIStateFlow = MutableStateFlow<PostUIState>(PostUIState.None)
    val confirmOrderUIStateFlow = _confirmOrderUIStateFlow.asStateFlow()

    fun confirmDelivery(id: Int) {
        coroutineScope.launch {
            Apis.Order.confirmDelivery(id)
                .onStart {
                    _confirmOrderUIStateFlow.emit(PostUIState.Loading)
                }
                .catch {
                    _confirmOrderUIStateFlow.emit(PostUIState.Error(it))
                    delay(2000L)
                    it.printStackTrace()
                    _confirmOrderUIStateFlow.emit(PostUIState.None)
                }
                .collect {
                    _confirmOrderUIStateFlow.emit(PostUIState.Success)
                    delay(2000L)
                    _confirmOrderUIStateFlow.emit(PostUIState.None)
                }
        }
    }

    private val _changeOrderUIStateFlow = MutableStateFlow<PostUIState>(PostUIState.None)
    val changeOrderUIStateFlow = _changeOrderUIStateFlow.asStateFlow()

    fun changeOrder(id: Int,name:String,address:String,phone:String,note:String) {
        coroutineScope.launch {
            Apis.Order.modifierOrder(id,name,address,phone,note)
                .onStart {
                    _changeOrderUIStateFlow.emit(PostUIState.Loading)
                }
                .catch {
                    _changeOrderUIStateFlow.emit(PostUIState.Error(it))
                    delay(2000L)
                    it.printStackTrace()
                    _changeOrderUIStateFlow.emit(PostUIState.None)
                }
                .collect {
                    _changeOrderUIStateFlow.emit(PostUIState.Success)
                    delay(2000L)
                    _changeOrderUIStateFlow.emit(PostUIState.None)
                    loadOrders()
                }
        }
    }
}
