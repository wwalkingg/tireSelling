package feature.home.me

import SettingItem
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeScreen(modifier: Modifier = Modifier, component: MeComponent) {
    val userInfoLoadState by component.modelState.userInfoLoadStateFlow.collectAsState()
    Column(modifier = modifier) {
        UserInfo(
            modifier = Modifier.fillMaxWidth().padding(10.dp, 20.dp),
            userInfoLoadState
        )
        Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceVariant)) {
            Spacer(Modifier.height(10.dp))
            SettingItem(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("收藏") },
                icon = { Icon(androidx.compose.material.icons.Icons.Default.Info, contentDescription = null) },
                onClick = component.modelState::onCollectClick
            )
            Divider(modifier = Modifier.background(MaterialTheme.colorScheme.surface).padding(start = 44.dp))
            SettingItem(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("修改用户信息") },
                icon = { Icon(androidx.compose.material.icons.Icons.Default.AccountCircle, contentDescription = null) },
                onClick = component.modelState::onModifierUserInfoClick
            )
            Divider(modifier = Modifier.background(MaterialTheme.colorScheme.surface).padding(start = 44.dp))
            SettingItem(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("修改密码") },
                onClick = component.modelState::onModifierPasswordClick
            )
            Divider(modifier = Modifier.background(MaterialTheme.colorScheme.surface).padding(start = 44.dp))
            SettingItem(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("退出登录") },
                onClick = component.modelState::onLogoutClick
            )
        }
    }
}
