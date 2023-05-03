package com.example.feature.home.recommends

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecommendsTopBar(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit,
    selectedTabIndex: Int,
    onTabRowSelected: (Int) -> Unit
) {
    Column(modifier) {
        Text("TireSelling", fontStyle = FontStyle.Italic, fontFamily = FontFamily.Cursive, fontSize = 25.sp)
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.clip(MaterialTheme.shapes.medium).background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxWidth().padding(horizontal = 10.dp).clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onSearchClick
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimaryContainer) {
                Text("输入产品关键字进行搜索")
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
        TabRow(
            modifier = Modifier.padding(top = 10.dp),
            selectedTabIndex = selectedTabIndex,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = Color.Transparent
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { onTabRowSelected(0) },
                text = { Text("推荐") },
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { onTabRowSelected(1) },
                text = { Text("文章咨询") },
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}