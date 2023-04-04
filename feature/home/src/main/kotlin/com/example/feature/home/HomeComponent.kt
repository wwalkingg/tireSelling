package com.example.feature.home

import core.component_base.ModelState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.DefaultMonotonicFrameClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.example.feature.home.category.CategoryComponent
import com.example.feature.home.me.MeComponent
import com.example.feature.home.recommends.RecommendsComponent
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = HomeModelState()

    internal val categoryComponent = CategoryComponent(childContext("category"))
    internal val meComponent = MeComponent(childContext("Me"))
    internal val recommendsComponent = RecommendsComponent(childContext("Recommends"))

}

internal class HomeModelState : ModelState() {
    @OptIn(ExperimentalFoundationApi::class)
    internal val pagerState = PagerState()
    private var _selected: BottomMenus by mutableStateOf(BottomMenus.HOME)

    @OptIn(ExperimentalFoundationApi::class)
    var selected: BottomMenus
        set(value) {
            coroutineScope.launch {
                withContext(DefaultMonotonicFrameClock){
                    pagerState.animateScrollToPage(BottomMenus.values().indexOf(value))
                }
            }
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