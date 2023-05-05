package com.example.feature.home.me

import SmallLoadUIStateScaffold
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.example.android.core.model.User
import core.common.NavConfig
import core.common.navigation
import core.component_base.LoadUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeScreen(modifier: Modifier = Modifier, component: MeComponent) {
    Scaffold(modifier.padding(top = 10.dp)) { padding ->
        val loadUserInfoUIState by component.model.loadUserInfoUIStateFlow.collectAsState()
        Column(
            Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            SmallLoadUIStateScaffold(loadUserInfoUIState) {
                UserInfoBlock(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    userInfo = (loadUserInfoUIState as LoadUIState.Success<User>).data
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            SettingItem(title = "我的个人资料", description = "可以进行更改或查看", onClick = {
                navigation.push(NavConfig.ModifierUserinfo)
            })
            Divider()
            SettingItem(title = "我的订单", description = "我的订单", onClick = {
                navigation.push(NavConfig.OrderManagement)
            })
            Divider()
            SettingItem(title = "退出登录", onClick = {
                navigation.replaceAll(NavConfig.Login)
            })
        }
    }
}

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp)
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.surface)
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
                Text(title, style = MaterialTheme.typography.titleMedium)
                if (description != null) {
                    Text(description, style = MaterialTheme.typography.labelMedium)
                }
            }
        }
        IconButton(onClick = { }) {
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }

}