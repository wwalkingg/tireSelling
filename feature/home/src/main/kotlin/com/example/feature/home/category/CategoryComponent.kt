package com.example.feature.home.category

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Category
import com.example.android.core.model.Product
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.network.api.Apis
import core.network.api.getAllCategories
import core.network.api.getProducts
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CategoryComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = CategoryModelState()
}

internal class CategoryModelState : ModelState() {
    private var _selectedCategoryId by mutableStateOf(1)
    var selectedCategoryId
        get() = _selectedCategoryId
        set(value) {
            _selectedCategoryId = value
            loadProducts(value)
        }

    private val _loadCategoriesUIStateFlow =
        MutableStateFlow<LoadUIState<List<Category>>>(LoadUIState.Loading)
    val loadCategoriesUIStateFlow = _loadCategoriesUIStateFlow.asStateFlow()

    private val _loadProductsUIStateFlow =
        MutableStateFlow<LoadUIState<List<Product>>>(LoadUIState.Loading)
    val loadProductsUIStateFlow = _loadProductsUIStateFlow.asStateFlow()

    private val _productsLoaded = mutableMapOf<Int, List<Product>>()

    init {
        loadCategories()
    }

    fun loadCategories() {
        coroutineScope.launch {
            _loadCategoriesUIStateFlow.emit(LoadUIState.Loading)
            Apis.Category.getAllCategories().catch {
                _loadCategoriesUIStateFlow.emit(LoadUIState.Error(it))
            }.collect {
                _loadCategoriesUIStateFlow.emit(LoadUIState.Loaded(it))
                loadProducts(it.first().id)
            }

        }
    }

    fun loadProducts(categoryId: Int) {
        coroutineScope.launch {
            _loadProductsUIStateFlow.emit(LoadUIState.Loading)
            // find in map first
            if (_productsLoaded.containsKey(categoryId)) {
                Log.i("CategoryModelState", "loadProducts: $categoryId (from map)")
                _loadProductsUIStateFlow.emit(
                    LoadUIState.Loaded(
                        _productsLoaded[categoryId]!!
                    )
                )
                cancel()
            }
            Log.i("CategoryModelState", "loadProducts: $categoryId")
            Apis.Product.getProducts(categoryId).catch {
                _loadProductsUIStateFlow.emit(LoadUIState.Error(it))
                Log.e("CategoryModelState", "loadProducts: ", it)
            }.collect {
                _loadProductsUIStateFlow.emit(LoadUIState.Loaded(it))
                _productsLoaded[categoryId] = it
            }
        }
    }
}
