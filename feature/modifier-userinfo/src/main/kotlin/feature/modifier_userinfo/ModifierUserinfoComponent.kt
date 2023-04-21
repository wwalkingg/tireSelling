package feature.modifier_userinfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.UserInfo
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.component_base.PostUIState
import core.network.api.Apis
import core.network.api.getUserInfo
import core.network.api.modifierUserinfo
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ModifierUserinfoComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = ModifierUserinfoModelState()
}

internal class ModifierUserinfoModelState : ModelState() {
    private val _loadUserInfoUIStateFlow = MutableStateFlow<LoadUIState<UserInfo>>(LoadUIState.Loading)
    val loadUserInfoUIStateFlow = _loadUserInfoUIStateFlow.asStateFlow()

    var newUserinfo by mutableStateOf<UserInfo?>(null)
    private val _modifierResultUIStateFlow = MutableStateFlow<PostUIState>(PostUIState.None)
    val modifierResultUIStateFlow = _modifierResultUIStateFlow.asStateFlow()

    init {
        loadUserInfo()
    }

    fun loadUserInfo() {
        coroutineScope.launch {
            Apis.Auth.getUserInfo()
                .onStart { _loadUserInfoUIStateFlow.value = LoadUIState.Loading }
                .catch {
                    _loadUserInfoUIStateFlow.value = LoadUIState.Error(it)
                }
                .collect {
                    newUserinfo = it
                    _loadUserInfoUIStateFlow.value = LoadUIState.Success(it)
                }
        }

    }

    fun modifier() {
        coroutineScope.launch {
            Apis.Auth.modifierUserinfo(newUserinfo!!)
                .onStart { _modifierResultUIStateFlow.value = PostUIState.Loading }
                .catch {
                    _modifierResultUIStateFlow.value = PostUIState.Error(it)
                }
                .collect {
                    _modifierResultUIStateFlow.value = PostUIState.Success
                }
        }
    }
}
