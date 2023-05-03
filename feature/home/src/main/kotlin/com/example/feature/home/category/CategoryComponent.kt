package com.example.feature.home.category

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.example.android.core.model.Brand
import com.example.android.core.model.Model
import com.example.android.core.model.Product
import com.example.feature.home.category.sort.SortComponent
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.network.api.Apis
import core.network.api.getAllBrands
import core.network.api.getAllModels
import core.network.api.getRank
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CategoryComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext {
    internal val modelState = CategoryModelState()

    val sortComponent = SortComponent(childContext("sort"))
}

internal class CategoryModelState() : ModelState() {
    private val _loadRankUIStateFlow = MutableStateFlow<LoadUIState<List<Product>>>(LoadUIState.Loading)
    val loadRankUIStateFlow = _loadRankUIStateFlow.asStateFlow()

    private val _loadAllModelsUIStateFlow = MutableStateFlow<LoadUIState<List<Model>>>(LoadUIState.Loading)
    val loadAllModelsUIStateFlow = _loadAllModelsUIStateFlow.asStateFlow()

    private val _loadAllBrandsUIStateFlow = MutableStateFlow<LoadUIState<List<Brand>>>(LoadUIState.Loading)
    val loadAllBrandsUIStateFlow = _loadAllBrandsUIStateFlow.asStateFlow()

    fun loadRank() {
        coroutineScope.launch {
            Apis.Product.getRank()
                .onStart { _loadRankUIStateFlow.emit(LoadUIState.Loading) }
                .catch {
                    it.printStackTrace()
                    _loadRankUIStateFlow.emit(LoadUIState.Error(it))
                }.collect {
                    _loadRankUIStateFlow.emit(LoadUIState.Success(it))
                }
        }
    }

    fun loadAllModels() {
        coroutineScope.launch {
            Apis.Model.getAllModels()
                .onStart { _loadAllModelsUIStateFlow.emit(LoadUIState.Loading) }
                .catch {
                    it.printStackTrace()
                    _loadAllModelsUIStateFlow.emit(LoadUIState.Error(it))
                }.collect {
                    _loadAllModelsUIStateFlow.emit(LoadUIState.Success(it))
                }
        }
    }

    fun loadAllBrands() {
        coroutineScope.launch {
            Apis.Brand.getAllBrands()
                .onStart { _loadAllBrandsUIStateFlow.emit(LoadUIState.Loading) }
                .catch {
                    it.printStackTrace()
                    _loadAllBrandsUIStateFlow.emit(LoadUIState.Error(it))
                }.collect {
                    _loadAllBrandsUIStateFlow.emit(LoadUIState.Success(it))
                }
        }
    }


    init {
        loadRank()
        loadAllModels()
        loadAllBrands()
    }
}
