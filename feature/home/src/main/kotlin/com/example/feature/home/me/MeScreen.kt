package com.example.feature.home.me

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feature.home.UserInfoBlock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeScreen(modifier: Modifier = Modifier, component: MeComponent) {
    Scaffold(modifier.padding(top = 10.dp)) { padding ->
        Column(
            Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            UserInfoBlock(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            SettingItem(title = "收货地址", description = "管理我的收获地址")
            SettingItem(title = "收藏的商品", description = "收藏的商品列表")
            SettingItem(title = "关注的店铺", description = "关注的农名店铺")
            SettingItem(title = "我的积分")
            SettingItem(title = "我的订单", description = "我的订单")
            SettingItem(title = "退出登录")
        }
    }
}

@Composable
fun SettingItem(modifier: Modifier = Modifier, title: String, description: String? = null) {
    Row(
        modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp)
            .clickable { }
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