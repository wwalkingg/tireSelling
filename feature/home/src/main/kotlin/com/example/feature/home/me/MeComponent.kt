package com.example.feature.home.me

import core.component_base.ModelState
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.UserInfo
import core.component_base.LoadUIState
import core.network.api.Apis
import core.network.api.getUserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MeComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val model = MeModelState()
}

internal class MeModelState : ModelState() {
    private val _loadUserInfoUIStateFlow = MutableStateFlow<LoadUIState<UserInfo>>(LoadUIState.Loading)
    val loadUserInfoUIStateFlow = _loadUserInfoUIStateFlow.asStateFlow()

    init {
        loadUserInfo()
    }

    fun loadUserInfo() {
        coroutineScope.launch {
            Apis.Auth.getUserInfo()
                .onStart { _loadUserInfoUIStateFlow.emit(LoadUIState.Loading) }
                .catch { _loadUserInfoUIStateFlow.emit(LoadUIState.Error(it)) }
                .collect { _loadUserInfoUIStateFlow.emit(LoadUIState.Success(it)) }
        }
    }
}
