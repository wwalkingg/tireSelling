package feature.home.me

import ModelState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.subscribe
import core.model.UserInfo
import core.network.utils.error
import core.network.utils.success
import httpClient
import io.ktor.client.request.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MeComponent(componentContext: ComponentContext) : ComponentContext by componentContext {

    internal val modelState = MeModelState()

    init {
        lifecycle.subscribe(
            onResume = { modelState.loadUserInfo() }
        )
    }

}

internal class MeModelState : ModelState() {
    private val _userInfoLoadStateFlow = MutableStateFlow<UserInfoLoadState>(UserInfoLoadState.Loading)
    val userInfoLoadStateFlow = _userInfoLoadStateFlow.asStateFlow()


    internal fun loadUserInfo() {
        coroutineScope.launch {
            _userInfoLoadStateFlow.emit(UserInfoLoadState.Loading)
            httpClient.get("/filter/getUserById").success<UserInfo> {
                _userInfoLoadStateFlow.emit(UserInfoLoadState.Success(it))
            }.error {
                _userInfoLoadStateFlow.emit(UserInfoLoadState.Error)
            }
        }
    }

    fun onCollectClick() {}

    fun onModifierUserInfoClick() {}

    fun onModifierPasswordClick() {}

    fun onLogoutClick() {}
}