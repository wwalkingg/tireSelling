package feature.store_detail

import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.*
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.network.api.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class StoreDetailComponent(componentContext: ComponentContext, val id: Int) :
    ComponentContext by componentContext {
    internal val modelState = StoreDetailModelState(id)
}

internal class StoreDetailModelState(val id: Int) : ModelState() {
//    private val _loadStoreUIStateFlow = MutableStateFlow<LoadUIState<Store>>(LoadUIState.Loading)
//    val loadStoreUIStateFlow = _loadStoreUIStateFlow.asStateFlow()
//
//    private val _loadStoreProductsUIStateFlow = MutableStateFlow<LoadUIState<List<Product>>>(LoadUIState.Loading)
//    val loadStoreProductsUIStateFlow = _loadStoreProductsUIStateFlow.asStateFlow()
//
//    private val _loadStoreActivitiesUIStateFlow =
//        MutableStateFlow<LoadUIState<List<StoreActivity>>>(LoadUIState.Loading)
//    val loadStoreActivitiesUIStateFlow = _loadStoreActivitiesUIStateFlow.asStateFlow()
//
//    init {
//        loadStoreDetail()
//        loadStoreComments()
//        loadStoreProducts()
//        loadStoreActivities()
//    }
//
//    fun loadStoreDetail() {
//        coroutineScope.launch {
//            Apis.Store.getStore(id)
//                .onStart { _loadStoreUIStateFlow.value = LoadUIState.Loading }
//                .catch {
//                    _loadStoreUIStateFlow.value = LoadUIState.Error(it)
//                    it.printStackTrace()
//                }
//                .collect { store ->
//                    _loadStoreUIStateFlow.value = LoadUIState.Success(store)
//                }
//        }
//    }
//
//    fun loadStoreComments() {
//        coroutineScope.launch {
//            Apis.Store.getStoreComments(id)
//                .onStart { _loadStoreCommentsUIStateFlow.value = LoadUIState.Loading }
//                .catch {
//                    _loadStoreCommentsUIStateFlow.value = LoadUIState.Error(it)
//                    it.printStackTrace()
//                }
//                .collect { comments ->
//                    _loadStoreCommentsUIStateFlow.value = LoadUIState.Success(comments)
//                }
//        }
//    }
//
//    fun loadStoreProducts() {
//        coroutineScope.launch {
//            Apis.Store.getStoreProducts(id)
//                .onStart { _loadStoreProductsUIStateFlow.value = LoadUIState.Loading }
//                .catch {
//                    _loadStoreProductsUIStateFlow.value = LoadUIState.Error(it)
//                    it.printStackTrace()
//                }
//                .collect { comments ->
//                    _loadStoreProductsUIStateFlow.value = LoadUIState.Success(comments)
//                }
//        }
//    }
//
//    fun loadStoreActivities() {
//        coroutineScope.launch {
//            Apis.Store.getStoreActivities(id)
//                .onStart { _loadStoreActivitiesUIStateFlow.value = LoadUIState.Loading }
//                .catch {
//                    _loadStoreActivitiesUIStateFlow.value = LoadUIState.Error(it)
//                    it.printStackTrace()
//                }
//                .collect { comments ->
//                    _loadStoreActivitiesUIStateFlow.value = LoadUIState.Success(comments)
//                }
//        }
//    }
}