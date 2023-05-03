import com.arkivanov.decompose.ComponentContext
import core.component_base.ModelState

class CollectionProductComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = CollectionProductModelState()
}

internal class CollectionProductModelState : ModelState() {
//    private val _loadCollectedProductsUIStateFlow = MutableStateFlow<LoadUIState<List<Product>>>(LoadUIState.Loading)
//    val loadCollectedProductsUIStateFlow = _loadCollectedProductsUIStateFlow.asStateFlow()
//
//    var canceledList = mutableStateListOf<Int>()
//    init {
//        loadCollectedProducts()
//    }
//
//    fun loadCollectedProducts() {
//        coroutineScope.launch {
//            Apis.Product.getCollectedProducts()
//                .onStart { _loadCollectedProductsUIStateFlow.emit(LoadUIState.Loading) }
//                .catch { _loadCollectedProductsUIStateFlow.emit(LoadUIState.Error(it)) }
//                .collect { _loadCollectedProductsUIStateFlow.emit(LoadUIState.Success(it)) }
//        }
//    }
//
//    fun cancelCollectProduct(context: Context, id: Int) {
//        coroutineScope.launch {
//            Apis.Product.cancelCollectProduct(id)
//                .catch {
//                    MainScope().launch {
//                        Toast.makeText(context, "操作失败", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                .collect{
//                    MainScope().launch {
//                        canceledList.add(id)
//                        Toast.makeText(context, "操作成功", Toast.LENGTH_SHORT).show()
//                    }
//                }
//        }
//    }
}
