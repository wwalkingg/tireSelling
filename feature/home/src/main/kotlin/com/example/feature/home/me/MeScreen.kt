package com.example.feature.home.me

import SmallLoadUIStateScaffold
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import com.example.android.core.model.UserInfo
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
            SmallLoadUIStateScaffold(loadUserInfoUIState){
                UserInfoBlock(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    userInfo = (loadUserInfoUIState as LoadUIState.Success<UserInfo>).data
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            SettingItem(title = "修改个人资料", description = "修改资料方便我们对你进行服务", onClick = {
                navigation.push(NavConfig.ModifierUserinfo)
            })
            SettingItem(
                title = "收货地址",
                description = "管理我的收获地址",
                onClick = { navigation.push(NavConfig.AddressManagement) })
            SettingItem(title = "收藏的商品", description = "收藏的商品列表", onClick = {
                navigation.push(NavConfig.CollectionProduct)
            })
            SettingItem(title = "我的订单", description = "我的订单", onClick = {
                navigation.push(NavConfig.OrderManagement)
            })
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
            .padding(10.dp)
            .background(MaterialTheme.colorScheme.surface),
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