package feature.person_health

import ModelState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import core.model.UserInfo
import core.network.utils.error
import core.network.utils.success
import httpClient
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PersonHealthComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    val modelState = instanceKeeper.getOrCreate { PersonHealthModelState() }
}

class PersonHealthModelState : ModelState() {
    val snackBarState = SnackbarHostState()
    var isPageLoading by mutableStateOf(false)

    private val _loadUserInfoUIStateFlow = MutableStateFlow<LoadUserInfoUIState>(LoadUserInfoUIState.Loading)
    internal val loadUserInfoUIStateFlow = _loadUserInfoUIStateFlow.asStateFlow()

    private val _age = mutableStateOf("")
    var age
        get() = _age.value
        set(value) {
            _age.value = value
            caculateBMI()
        }
    private val _height = mutableStateOf("")
    var height
        get() = _height.value
        set(value) {
            _height.value = value
            caculateBMI()
        }

    private val _weight = mutableStateOf("")
    var weight
        get() = _weight.value
        set(value) {
            _weight.value = value
            caculateBMI()
        }

    private var _bmi = mutableStateOf("")
    val bmi get() = _bmi.value

    var userInfo: UserInfo? = null
    fun caculateBMI() {
        try {
            val heightInMeters = height.toFloat() / 100
            val bmi0 = weight.toFloat() / (heightInMeters * heightInMeters)
            _bmi.value = bmi0.toString()
        } catch (e: Exception) {
            _bmi.value = "Error"
        }
    }

    init {
        loadUserInfo()
    }

    fun loadUserInfo() {
        coroutineScope.launch {
            _loadUserInfoUIStateFlow.emit(LoadUserInfoUIState.Loading)
            httpClient.get("/filter/getUserById").success<UserInfo> {
                age = it.age.toString()
                height = (it.height ?: 180).toString()
                weight = (it.weight ?: 80f).toString()
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
                isPageLoading = true
                httpClient.post("/filter/updateUserInfo") {
                    contentType(ContentType.Application.Json)
                    setBody(
                        userInfo!!.copy(
                            age = age.toInt(),
                            height = height.toInt(),
                            weight = weight.toFloat(),
                        )
                    )
                }.success {
                    isPageLoading = false
                    snackBarState.showSnackbar("修改成功", withDismissAction = true)
                }
            }
        } catch (e: Exception) {
            isPageLoading = false
            e.printStackTrace()
            coroutineScope.launch {
                snackBarState.showSnackbar("数据格式错误", withDismissAction = true)
            }
        } finally {
            isPageLoading = false
        }
    }

    init {
        caculateBMI()
    }
}


internal sealed interface LoadUserInfoUIState {
    object Loading : LoadUserInfoUIState
    data class Success(val data: UserInfo) : LoadUserInfoUIState
    object Error : LoadUserInfoUIState
}