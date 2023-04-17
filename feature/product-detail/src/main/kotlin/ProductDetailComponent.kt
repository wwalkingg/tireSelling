import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Product
import com.example.android.core.model.ProductAndStore
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.network.api.Apis
import core.network.api.collectProduct
import core.network.api.getProduct
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
    val collectClickFlow = MutableSharedFlow<Nothing?>()

    val snackBarState = SnackbarHostState()
    var isCollected by mutableStateOf(false)


    init {
        loadProductDetail()
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

    fun collectProduct() {
        coroutineScope.launch {
            collectClickFlow.emit(null)
        }
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
}
