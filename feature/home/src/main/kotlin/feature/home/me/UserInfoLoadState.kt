package feature.home.me

import core.model.UserInfo

sealed interface UserInfoLoadState {
    object Loading : UserInfoLoadState
    object Error : UserInfoLoadState
    data class Success(val userInfo: UserInfo) : UserInfoLoadState
}