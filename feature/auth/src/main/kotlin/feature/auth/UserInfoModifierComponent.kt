package feature.auth

import ModelState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import core.model.UserInfo
import core.network.utils.error
import core.network.utils.success
import httpClient
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.InputStream
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserInfoModifierComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = UserInfoModifierModelState()
}

internal class UserInfoModifierModelState : ModelState() {
    private val _loadUserInfoUIStateFlow = MutableStateFlow<LoadUserInfoUIState>(LoadUserInfoUIState.Loading)
    val loadUserInfoUIStateFlow = _loadUserInfoUIStateFlow.asStateFlow()

    private val _modifierUserInfoUIStateFlow = MutableStateFlow<ModifierUserInfoUIState>(ModifierUserInfoUIState.Wait)
    val modifierUserInfoUIStateFlow = _modifierUserInfoUIStateFlow.asStateFlow()

    var newAvatarInputStream by mutableStateOf<InputStream?>(null)
    var newUserInfo by mutableStateOf<UserInfo?>(null)

    init {
        loadUserInfo()
    }

    fun loadUserInfo() {
        coroutineScope.launch {
            _loadUserInfoUIStateFlow.emit(LoadUserInfoUIState.Loading)
            httpClient.get("/filter/getUserById").success<UserInfo> {
                newUserInfo = it
                _loadUserInfoUIStateFlow.emit(LoadUserInfoUIState.Success(it))
            }.error {
                _loadUserInfoUIStateFlow.emit(LoadUserInfoUIState.Error)
            }
        }
    }

    fun update() {
        coroutineScope.launch {
            _modifierUserInfoUIStateFlow.emit(ModifierUserInfoUIState.Loading)
            val updateAvatarResult = async { updateAvatar() }
            val modifierUserInfoResult = async { modifierUserInfo() }
            with(updateAvatarResult.await() to modifierUserInfoResult.await()) {
                if (first && second) _modifierUserInfoUIStateFlow.emit(ModifierUserInfoUIState.Success)
                else _modifierUserInfoUIStateFlow.emit(ModifierUserInfoUIState.Error)
            }
        }
    }

    suspend fun updateAvatar(): Boolean = suspendCoroutine { continuation ->
        coroutineScope.launch {
            if (newAvatarInputStream != null) {
                httpClient.post("/filter/uploadAvatar") {
                    setBody(MultiPartFormDataContent(
                        formData {
                            append(
                                "file",
                                newAvatarInputStream!!.readBytes()
                            )
                        }
                    ))
                }.success {
                    continuation.resume(true)
                }.error {
                    continuation.resume(false)
                }
            } else continuation.resume(true)
        }
    }

    suspend fun modifierUserInfo(): Boolean = suspendCoroutine { continuation ->
        coroutineScope.launch {
            httpClient.post("/filter/updateUserInfo") {
                contentType(ContentType.Application.Json)
                setBody(newUserInfo)
            }.success { continuation.resume(true) }.error {
                continuation.resume(false)
            }
        }
    }
}

internal sealed interface LoadUserInfoUIState {
    object Loading : LoadUserInfoUIState
    data class Success(val data: UserInfo) : LoadUserInfoUIState
    object Error : LoadUserInfoUIState
}

internal sealed interface ModifierUserInfoUIState {
    object Wait : ModifierUserInfoUIState
    object Loading : ModifierUserInfoUIState
    object Success : ModifierUserInfoUIState
    object Error : ModifierUserInfoUIState
}