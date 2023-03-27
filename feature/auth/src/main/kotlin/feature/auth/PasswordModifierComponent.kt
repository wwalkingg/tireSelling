package feature.auth

import ModelState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import core.network.utils.error
import core.network.utils.success
import httpClient
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PasswordModifierComponent(componentContext: ComponentContext) : ComponentContext by componentContext {

    internal val modelState = ModifierPasswordModelState()
}

internal class ModifierPasswordModelState : ModelState() {
    private val _modifierPasswordUIStateFlow =
        MutableStateFlow<ModifierPasswordUIState>(ModifierPasswordUIState.Wait)
    internal val modifierPasswordUIStateFlow = _modifierPasswordUIStateFlow.asStateFlow()

    var checkMsg by mutableStateOf("")
    var oldPassword by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var confirmNewPassword by mutableStateOf("")

    fun modifierPassword() {
        coroutineScope.launch {
            if (confirmField()) {
                _modifierPasswordUIStateFlow.emit(ModifierPasswordUIState.Loading)
                httpClient.post("/filter/updatePassword") {
                    contentType(ContentType.Application.Json)
                    setBody(
                        mapOf(
                            "oldPassword" to oldPassword,
                            "newPassword" to newPassword
                        )
                    )
                }.success {
                    _modifierPasswordUIStateFlow.emit(ModifierPasswordUIState.Success)
                }.error {
                    _modifierPasswordUIStateFlow.emit(ModifierPasswordUIState.Error("修改失败"))
                }
            }
        }
    }

    fun confirmField(): Boolean {
        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            checkMsg = "请填写完整信息"
            return false
        }
        if (newPassword.length < 6) {
            checkMsg = "密码长度不得小于6位"
            return false
        }
        if (newPassword != confirmNewPassword) {
            checkMsg = "两次输入的密码不一致"
            return false
        }
        checkMsg = ""
        return true
    }
}

internal sealed interface ModifierPasswordUIState {
    object Wait : ModifierPasswordUIState
    object Loading : ModifierPasswordUIState
    object Success : ModifierPasswordUIState
    data class Error(val msg: String) : ModifierPasswordUIState
}