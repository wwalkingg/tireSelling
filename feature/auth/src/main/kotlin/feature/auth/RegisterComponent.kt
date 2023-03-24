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
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import settings

class RegisterComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
}

class RegisterModelState : ModelState() {
    var id by mutableStateOf("")
    var password by mutableStateOf("")

    private val _loginUIStateFlow = MutableStateFlow<LoginUIState>(LoginUIState.Wait)
    val loginUIStateFlow = _loginUIStateFlow.asStateFlow()
    fun login() {
        coroutineScope.launch {
            _loginUIStateFlow.emit(LoginUIState.Loading)
            httpClient.post("/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginParameter(id, password))
            }.success<LoginResp> {
                settings.set("token", it.token)
                _loginUIStateFlow.emit(LoginUIState.Success(it))
                launch(Dispatchers.Main) {
                    rootNavigation.replaceAll(Config.RootConfig.Home)
                }
            }.error {
                _loginUIStateFlow.emit(LoginUIState.Error("登录失败"))
                delay(2000L)
                _loginUIStateFlow.emit(LoginUIState.Wait)
            }
        }
    }
}

sealed interface RegisterUIState {
    object Wait : RegisterUIState
    object Loading : RegisterUIState
    data class Error(val message: String) : RegisterUIState
    data class Success(val loginResp: LoginResp) : RegisterUIState
}