package com.example.feature.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
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
    CATEGORY("分类", Icons.Rounded.TireRepair, Icons.Rounded.TireRepair),
    COUPON_PACKAGE("卡包",Icons.Rounded.CardGiftcard,Icons.Rounded.CardGiftcard),
    ME("我的", Icons.Rounded.SupervisedUserCircle, Icons.Rounded.SupervisedUserCircle)
}