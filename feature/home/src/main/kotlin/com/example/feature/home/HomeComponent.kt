package com.example.feature.home

import ModelState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

class HomeComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = HomeModelState()
}

internal class HomeModelState : ModelState() {
    var selected: BottomMenus by mutableStateOf(BottomMenus.HOME)

}

internal sealed interface LoadUserInfoUIState {
    object Wait:LoadUserInfoUIState
    object Fail:LoadUserInfoUIState
    object Loading:LoadUserInfoUIState
//    data class Success(val userInfo:UserInfo):LoadUserInfoUIState
}