package feature.modifier_userinfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.User
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.component_base.PostUIState
import core.network.api.Apis
import core.network.api.getUserInfo
import core.network.api.modifierUserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ModifierUserinfoComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = ModifierUserinfoModelState()
}

internal class ModifierUserinfoModelState : ModelState() {
    private val _loadUserInfoUIStateFlow = MutableStateFlow<LoadUIState<User>>(LoadUIState.Loading)
    val loadUserInfoUIStateFlow = _loadUserInfoUIStateFlow.asStateFlow()

    var newUserinfo by mutableStateOf<User?>(null)
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
            Apis.Auth.modifierUserInfo(newUserinfo!!.id, newUserinfo!!)
                .onStart { _modifierResultUIStateFlow.value = PostUIState.Loading }
                .catch {
                    _modifierResultUIStateFlow.value = PostUIState.Error(it)
                }
                .collect {
                    _modifierResultUIStateFlow.value = PostUIState.Success("修改成功")
                }
        }
    }
}
