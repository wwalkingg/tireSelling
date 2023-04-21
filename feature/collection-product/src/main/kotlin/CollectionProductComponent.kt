import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Product
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.network.api.Apis
import core.network.api.cancelCollectProduct
import core.network.api.collectProduct
import core.network.api.getCollectedProducts
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CollectionProductComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = CollectionProductModelState()
}

internal class CollectionProductModelState : ModelState() {
    private val _loadCollectedProductsUIStateFlow = MutableStateFlow<LoadUIState<List<Product>>>(LoadUIState.Loading)
    val loadCollectedProductsUIStateFlow = _loadCollectedProductsUIStateFlow.asStateFlow()

    var canceledList = mutableStateListOf<Int>()
    init {
        loadCollectedProducts()
    }

    fun loadCollectedProducts() {
        coroutineScope.launch {
            Apis.Product.getCollectedProducts()
                .onStart { _loadCollectedProductsUIStateFlow.emit(LoadUIState.Loading) }
                .catch { _loadCollectedProductsUIStateFlow.emit(LoadUIState.Error(it)) }
                .collect { _loadCollectedProductsUIStateFlow.emit(LoadUIState.Success(it)) }
        }
    }

    fun cancelCollectProduct(context: Context, id: Int) {
        coroutineScope.launch {
            Apis.Product.cancelCollectProduct(id)
                .catch {
                    MainScope().launch {
                        Toast.makeText(context, "操作失败", Toast.LENGTH_SHORT).show()
                    }
                }
                .collect{
                    MainScope().launch {
                        canceledList.add(id)
                        Toast.makeText(context, "操作成功", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
