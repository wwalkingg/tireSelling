package com.example.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.example.feature.home.category.CategoryComponent
import com.example.feature.home.coupons_package.CouponsPackageComponent
import com.example.feature.home.me.MeComponent
import com.example.feature.home.recommends.RecommendsComponent
import core.component_base.ModelState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Stable
class HomeComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = instanceKeeper.getOrCreate { HomeModelState() }

    internal val recommendsComponent = RecommendsComponent(childContext("Recommends"))
    internal val categoryComponent = CategoryComponent(childContext("category"))
    internal val meComponent = MeComponent(childContext("Me"))
    internal val couponsPackageComponent = CouponsPackageComponent(childContext("couponsPackage"))

//    fun jumpToCategory(id: Int) {
//        modelState.selected = BottomMenus.CATEGORY
//        categoryComponent.modelState._selectedCategoryId = id
//    }

}

@OptIn(ExperimentalFoundationApi::class)
internal class HomeModelState : ModelState() {
    @OptIn(ExperimentalFoundationApi::class)
    internal val pagerState = PagerState(1)
    private var _selected: BottomMenus by mutableStateOf(BottomMenus.HOME)

    var selected: BottomMenus
        set(value) {
            _selected = value
        }
        get() = _selected

}

internal sealed interface LoadUserInfoUIState {
    object Wait : LoadUserInfoUIState
    object Fail : LoadUserInfoUIState
    object Loading : LoadUserInfoUIState
//    data class Success(val userInfo:UserInfo):LoadUserInfoUIState
}