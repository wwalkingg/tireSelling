package feature.product_detail

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Address
import com.example.android.core.model.ProductAndStore
import com.example.android.core.model.ProductComment
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.datastore.AddressStore
import core.network.api.Apis
import core.network.api.getProduct
import core.network.api.getProductComments
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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

    val collectClickFlow = MutableSharedFlow<Nothing?>()

    val snackBarState = SnackbarHostState()
    var isCollected by mutableStateOf(false)


    init {
        loadProductDetail()
        loadProductComments()
        subscribeCollectProductFlow()
    }

    fun loadProductDetail() {
        coroutineScope.launch {
            Apis.Product.getProduct(id)
                .onStart { _loadProductDetailUIStateFlow.emit(LoadUIState.Loading) }
                .catch { _loadProductDetailUIStateFlow.emit(LoadUIState.Error(it)) }
                .collect {
                    isCollected = it.product.isFavorite
                    _loadProductDetailUIStateFlow.emit(LoadUIState.Loaded(it))
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
                    _loadCommentsUIStateFlow.emit(LoadUIState.Loaded(it))
                }
        }
    }

    fun collectProduct() {
        coroutineScope.launch {
            collectClickFlow.emit(null)
        }
    }

    fun buy() {

    }

    private fun subscribeCollectProductFlow() {
        coroutineScope.launch {
//           collectClickFlow.transform { isCollected = !isCollected }.debounce(1000).collect{
//               coroutineScope.launch {
//                   Apis.Product.collectProduct(id)
//                       .catch {
//                           snackBarState.showSnackbar(message = "收藏失败，请重试")
//                           isCollected = false
//                       }
//                       .collect {
//                           snackBarState.showSnackbar(message = "收藏成功")
//                           isCollected = true
//                       }
//               }
//           }
        }
    }

    fun loadAddressList(): List<Address> {
        return AddressStore.retrieve().addresses
    }

}
