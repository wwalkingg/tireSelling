package com.example.feature.home.me

import NavigationTopBar
import android.telecom.Call.Details
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeScreen(modifier: Modifier = Modifier, component: MeComponent) {
    Scaffold(modifier,topBar = { NavigationTopBar(title = "我的") }) { padding ->
        Column(Modifier.padding(padding)) {
            SettingItem(title = "收藏的商品", description = "收藏的文章、视频、音频、图片")
            SettingItem(title = "关注的店铺", description = "关注的人、话题、专栏")
            SettingItem(title = "我的积分")
            SettingItem(title = "我的订单", description = "我的订单")
        }
    }
}

@Composable
fun SettingItem(modifier: Modifier = Modifier, title: String, description: String? = null) {
    Column(modifier = modifier.heightIn(min = 40.dp).background(MaterialTheme.colorScheme.surface)) {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
            Text(title)
            if (description != null) {
                Text(description)
            }
        }
    }
}