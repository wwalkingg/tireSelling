package feature.auth

import ModelState
import androidx.compose.material3.SnackbarHostState
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
    var isLoading by mutableStateOf(false)
    val snackBarState = SnackbarHostState()
    private val _loadUserInfoUIStateFlow = MutableStateFlow<LoadUserInfoUIState>(LoadUserInfoUIState.Loading)
    val loadUserInfoUIStateFlow = _loadUserInfoUIStateFlow.asStateFlow()

    private val _modifierUserInfoUIStateFlow = MutableStateFlow<ModifierUserInfoUIState>(ModifierUserInfoUIState.Wait)
    val modifierUserInfoUIStateFlow = _modifierUserInfoUIStateFlow.asStateFlow()

    var userInfo: UserInfo? = null
    var name by mutableStateOf("")
    var age by mutableStateOf("")
    var sex by mutableStateOf("")
    var height by mutableStateOf("")
    var weight by mutableStateOf("")
    var isPartner by mutableStateOf(false)

    init {
        loadUserInfo()
    }

    fun loadUserInfo() {
        coroutineScope.launch {
            _loadUserInfoUIStateFlow.emit(LoadUserInfoUIState.Loading)
            httpClient.get("/filter/getUserById").success<UserInfo> {
                name = it.name ?: "未设置"
                age = it.age.toString()
                sex = (it.sex ?: 1).toString()
                height = (it.height ?: 180).toString()
                weight = (it.weight ?: 80f).toString()
                isPartner = it.isPartner
                userInfo = it
                _loadUserInfoUIStateFlow.emit(LoadUserInfoUIState.Success(it))
            }.error {
                _loadUserInfoUIStateFlow.emit(LoadUserInfoUIState.Error)
            }
        }
    }

    fun modifierUserInfo() {
        try {
            coroutineScope.launch {
                isLoading = true
                httpClient.post("/filter/updateUserInfo") {
                    contentType(ContentType.Application.Json)
                    setBody(
                        userInfo!!.copy(
                            name = name,
                            age = age.toInt(),
                            sex = sex.toInt(),
                            height = height.toInt(),
                            weight = weight.toFloat(),
                            isPartner = isPartner
                        )
                    )
                }.success {
                    isLoading = false
                    snackBarState.showSnackbar("修改成功", withDismissAction = true)
                }
            }
        } catch (e: Exception) {
            isLoading = false
            e.printStackTrace()
            coroutineScope.launch {
                snackBarState.showSnackbar("数据格式错误", withDismissAction = true)
            }
        } finally {
            isLoading = false
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