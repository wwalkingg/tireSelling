package com.example.feature.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
internal fun HomeBottomBar(modifier: Modifier = Modifier, selected: BottomMenus, onSelected: (BottomMenus) -> Unit) {
    NavigationBar(modifier) {
        BottomMenus.values().forEach {
            NavigationBarItem(
                selected = selected == it,
                onClick = { onSelected(it) },
                icon = { Icon(if (selected == it) it.selectedIcon else it.icon, contentDescription = null) },
            )
        }
    }
}

internal enum class BottomMenus(
    val chineseName: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    HOME("首页", Icons.Rounded.Home, Icons.Rounded.Home),
    CATEGORY("分类", Icons.Rounded.Category, Icons.Rounded.Category),
    ME("我的", Icons.Rounded.VerifiedUser, Icons.Rounded.VerifiedUser)
}