import android.util.Log
import com.arkivanov.decompose.ComponentContext
import core.component_base.ModelState

class LoginComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = LoginModelState()
}

internal class LoginModelState : ModelState() {
//    private val _loginUIStateFlow = MutableStateFlow<LoginUIState>(LoginUIState.None)
//    val loginUIStateFlow = _loginUIStateFlow.asStateFlow()
//    var username by mutableStateOf("")
//    var password by mutableStateOf("")
//    fun login() {
//        coroutineScope.launch {
//            Apis.Auth.login(username, password)
//                .onStart { _loginUIStateFlow.emit(LoginUIState.Loading) }
//                .catch {
//                    it.printStackTrace()
//                    _loginUIStateFlow.emit(LoginUIState.LoginError(it))
//                }
//                .collect {
//                    TokenStore(it).store()
//                    _loginUIStateFlow.emit(LoginUIState.LoginLoaded(it))
//                }
//        }
//    }
//
//    fun register() {
//        coroutineScope.launch {
//            Apis.Auth.register(username, password)
//                .onStart { _loginUIStateFlow.emit(LoginUIState.Loading) }
//                .catch {
//                    it.printStackTrace()
//                    _loginUIStateFlow.emit(LoginUIState.RegisterError(it))
//                }
//                .collect { _loginUIStateFlow.emit(LoginUIState.RegisterLoaded) }
//        }
//    }

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