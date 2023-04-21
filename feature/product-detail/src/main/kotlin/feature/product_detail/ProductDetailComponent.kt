package feature.product_detail

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.*
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.component_base.PostUIState
import core.datastore.AddressStore
import core.network.api.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Stable
class ProductDetailComponent(componentContext: ComponentContext, id: Int) :
    ComponentContext by componentContext {
    internal val modelState = ProductDetailModelState(id)
}

internal class ProductDetailModelState(private val id: Int) : ModelState() {
    private val _loadProductDetailUIStateFlow: MutableStateFlow<LoadUIState<ProductAndStore>> =
        MutableStateFlow(LoadUIState.Loading)
    val loadProductDetailUIStateFlow = _loadProductDetailUIStateFlow.asStateFlow()

    private val _loadCommentsUIStateFlow: MutableStateFlow<LoadUIState<List<ProductComment>>> =
        MutableStateFlow(LoadUIState.Loading)
    val loadCommentsUIStateFlow = _loadCommentsUIStateFlow.asStateFlow()

    private val _createOrderUIStateFlow: MutableStateFlow<PostUIState> =
        MutableStateFlow(PostUIState.None)
    val createOrderUIStateFlow = _createOrderUIStateFlow.asStateFlow()

    val collectClickFlow = MutableSharedFlow<Nothing?>()

    val snackBarState = SnackbarHostState()
    var isCollected by mutableStateOf(false)

    // -_-
    var storeId: Int = 0

    init {
        loadProductDetail()
        loadProductComments()
    }

    fun loadProductDetail() {
        coroutineScope.launch {
            Apis.Product.getProduct(id)
                .onStart { _loadProductDetailUIStateFlow.emit(LoadUIState.Loading) }
                .catch {
                    _loadProductDetailUIStateFlow.emit(LoadUIState.Error(it))
                    it.printStackTrace()
                }
                .collect {
                    storeId = it.store.id
                    isCollected = it.product.isFavorite
                    _loadProductDetailUIStateFlow.emit(LoadUIState.Success(it))
                }
        }
    }

    fun loadProductComments() {
        coroutineScope.launch {
            Apis.Product.getProductComments(id)
                .onStart { _loadCommentsUIStateFlow.emit(LoadUIState.Loading) }
                .catch {
                    _loadCommentsUIStateFlow.emit(LoadUIState.Error(it))
                    it.printStackTrace()
                }
                .collect {
                    _loadCommentsUIStateFlow.emit(LoadUIState.Success(it))
                }
        }
    }

    fun collectProduct(context: Context) {
        GlobalScope.launch {
            Apis.Product.collectProduct(CollectParam(null, id, storeId))
                .catch {
                    MainScope().launch {
                        Toast.makeText(context, "操作失败，请重试", Toast.LENGTH_SHORT).show()
                    }
                    isCollected = !isCollected
                }
                .collect {
                    MainScope().launch {
                        Toast.makeText(context, "操作成功", Toast.LENGTH_SHORT).show()
                    }
                    isCollected = true
                }
        }
    }

    fun buy(storeId: Int, productId: Int, price: Float, address: String) {
        coroutineScope.launch {
            Apis.Order.createOrder(OrderParam(storeId, productId, price, address = address))
                .onStart { _createOrderUIStateFlow.emit(PostUIState.Loading) }
                .catch {
                    _createOrderUIStateFlow.emit(PostUIState.Error(it))
                    it.printStackTrace()
                }
                .collect {
                    _createOrderUIStateFlow.emit(PostUIState.Success)
                    delay(2000L)
                    _createOrderUIStateFlow.emit(PostUIState.None)
                }
        }
    }

    fun loadAddressList(): List<Address> {
        return AddressStore.retrieve().addresses
    }
}
