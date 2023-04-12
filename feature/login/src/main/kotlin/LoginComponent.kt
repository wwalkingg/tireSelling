import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.UserInfo
import core.component_base.ModelState
import core.datastore.TokenStore
import core.network.api.Apis
import core.network.api.login
import core.network.api.register
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = LoginModelState()
}

internal class LoginModelState : ModelState() {
    private val _loginUIStateFlow = MutableStateFlow<LoginUIState>(LoginUIState.None)
    val loginUIStateFlow = _loginUIStateFlow.asStateFlow()
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    fun login() {
        coroutineScope.launch {
            Apis.Auth.login(username, password)
                .onStart { _loginUIStateFlow.emit(LoginUIState.Loading) }
                .catch {
                    it.printStackTrace()
                    _loginUIStateFlow.emit(LoginUIState.LoginError(it))
                }
                .collect {
                    TokenStore(it).store()
                    _loginUIStateFlow.emit(LoginUIState.LoginLoaded(it))
                }
        }
    }

    fun register() {
        coroutineScope.launch {
            Apis.Auth.register(username, password)
                .onStart { _loginUIStateFlow.emit(LoginUIState.Loading) }
                .catch {
                    it.printStackTrace()
                    _loginUIStateFlow.emit(LoginUIState.RegisterError(it))
                }
                .collect { _loginUIStateFlow.emit(LoginUIState.RegisterLoaded) }
        }
    }

}

sealed class LoginUIState {
    object Loading : LoginUIState()
    data class LoginError(val throwable: Throwable) : LoginUIState()
    data class LoginLoaded(val token: String) : LoginUIState()
    data class RegisterError(val throwable: Throwable) : LoginUIState()
    object RegisterLoaded : LoginUIState()
    object None : LoginUIState()

    init {
        Log.i("LoginUIState", "init: $this")
    }


}