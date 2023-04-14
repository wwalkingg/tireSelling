package feature.home.me

import ModelState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.subscribe
import core.common.navigation.Config
import core.common.navigation.rootNavigation
import core.model.UserInfo
import core.network.utils.error
import core.network.utils.success
import httpClient
import io.ktor.client.request.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import settings

class MeComponent(componentContext: ComponentContext) : ComponentContext by componentContext {

    internal val modelState = instanceKeeper.getOrCreate { MeModelState() }

    init {
        lifecycle.subscribe(
            onResume = {
                modelState.loadUserInfo()
            }
        )
    }

}

internal class MeModelState : ModelState() {
    private val _userInfoLoadStateFlow = MutableStateFlow<UserInfoLoadState>(UserInfoLoadState.Loading)
    val userInfoLoadStateFlow = _userInfoLoadStateFlow.asStateFlow()

    init {
        loadUserInfo()
    }

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

    fun onCollectClick() {
        rootNavigation.push(Config.RootConfig.MyCollect)
    }

    fun onModifierUserInfoClick() {
        rootNavigation.push(Config.RootConfig.UserInfoModifier)
    }

    fun onModifierPasswordClick() {
        rootNavigation.push(Config.RootConfig.PasswordModifier)
    }

    fun onLogoutClick() {
        settings.remove("token")
        rootNavigation.replaceAll(Config.RootConfig.Login)
    }
}