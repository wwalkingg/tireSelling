package feature.auth

import ModelState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.replaceAll
import com.russhwolf.settings.set
import core.common.navigation.Config
import core.common.navigation.rootNavigation
import core.model.LoginParameter
import core.model.LoginResp
import core.network.utils.error
import core.network.utils.success
import httpClient
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import settings

class RegisterComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = RegisterModelState()
}

class RegisterModelState : ModelState() {
    var id by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    private val _registerUIStateFlow = MutableStateFlow<RegisterUIState>(RegisterUIState.Wait)
    val registerUIStateFlow = _registerUIStateFlow.asStateFlow()
    fun register() {
        coroutineScope.launch {
            _registerUIStateFlow.emit(RegisterUIState.Loading)
            httpClient.post("/register") {
                contentType(ContentType.Application.Json)
                setBody(LoginParameter(id, password))
            }.success<LoginResp> {
                settings["token"] = it.token
                _registerUIStateFlow.emit(RegisterUIState.Success(it))
                launch(Dispatchers.Main) {
                    rootNavigation.replaceAll(Config.RootConfig.Home)
                }
            }.error {
                _registerUIStateFlow.emit(RegisterUIState.Error("注册失败"))
                delay(2000L)
                _registerUIStateFlow.emit(RegisterUIState.Wait)
            }
        }
    }
}

sealed interface RegisterUIState {
    object Wait : RegisterUIState
    object Loading : RegisterUIState
    data class Error(val message: String) : RegisterUIState
    data class Success(val registerResp: LoginResp) : RegisterUIState
}