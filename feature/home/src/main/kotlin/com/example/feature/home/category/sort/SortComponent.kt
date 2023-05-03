package com.example.feature.home.category.sort

import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Product
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.network.api.Apis
import core.network.api.getAllProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SortComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = SortModelState()
}

internal class SortModelState : ModelState() {
    private val _loadSortUIStateFlow = MutableStateFlow<LoadUIState<List<Product>>>(LoadUIState.Loading)
    val loadSortUIStateFlow = _loadSortUIStateFlow.asStateFlow()

    fun loadSort() {
        coroutineScope.launch {
            Apis.Product.getAllProducts()
                .onStart {
                    _loadSortUIStateFlow.emit(LoadUIState.Loading)
                }
                .catch {
                    it.printStackTrace()
                    _loadSortUIStateFlow.emit(LoadUIState.Error(it))
                }
                .collect { products ->
                    _loadSortUIStateFlow.emit(LoadUIState.Success(products))
                }
        }
    }

    init {
        loadSort()
    }
}