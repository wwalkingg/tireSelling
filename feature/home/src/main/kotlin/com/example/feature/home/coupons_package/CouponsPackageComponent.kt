package com.example.feature.home.coupons_package

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Coupon
import com.example.android.core.model.CouponPackage
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.network.api.Apis
import core.network.api.getAllCoupons
import core.network.api.getMyCouponPackages
import core.network.api.getMyCoupons
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@Stable
class CouponsPackageComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext {
    internal val modelState = CouponsPackageModelState()
}

internal class CouponsPackageModelState : ModelState() {

    private val _loadMyCouponsUIStateFlow =
        MutableStateFlow<LoadUIState<List<Coupon>>>(LoadUIState.Loading)
    val loadMyCouponsUIStateFlow = _loadMyCouponsUIStateFlow.asStateFlow()



    private val _loadMyCouponPackageUIStateFlow =
        MutableStateFlow<LoadUIState<List<CouponPackage>>>(LoadUIState.Loading)
    val loadMyCouponPackageUIStateFlow = _loadMyCouponPackageUIStateFlow.asStateFlow()



    fun loadMyCoupons() {
        coroutineScope.launch {
            Apis.Coupons.getMyCoupons()
                .onStart { _loadMyCouponsUIStateFlow.value = LoadUIState.Loading }
                .catch {
                    it.printStackTrace()
                    _loadMyCouponsUIStateFlow.value = LoadUIState.Error(it)
                }
                .collect {
                    _loadMyCouponsUIStateFlow.value = LoadUIState.Success(it)
                }
        }
    }


    fun loadMyCouponPackages() {
        coroutineScope.launch {
            Apis.Coupons.getMyCouponPackages()
                .onStart { _loadMyCouponPackageUIStateFlow.value = LoadUIState.Loading }
                .catch {
                    it.printStackTrace()
                    _loadMyCouponPackageUIStateFlow.value = LoadUIState.Error(it)
                }
                .collect {
                    _loadMyCouponPackageUIStateFlow.value = LoadUIState.Success(it)
                }
        }
    }




    init {
        loadMyCoupons()
        loadMyCouponPackages()
    }
}

