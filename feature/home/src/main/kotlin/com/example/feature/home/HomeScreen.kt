package com.example.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import design_system.icons.PhosphorIcons
import design_system.icons.phosphoricons.At

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, component: HomeComponent) {
    Scaffold(bottomBar = {
        HomeBottomBar(
            Modifier.fillMaxWidth(),
            component.modelState.selected,
            onSelected = { component.modelState.selected = it })
    }) { padding ->
        Column(Modifier.padding(padding)) {
            Icon(PhosphorIcons.At,contentDescription = null)
        }
    }
}