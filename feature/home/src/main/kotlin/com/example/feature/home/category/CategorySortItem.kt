package com.example.feature.home.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategorySortItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    title: String,
    onClick: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(10.dp)
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .background(if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)
            .padding(contentPadding)
    ) {
        CompositionLocalProvider(LocalContentColor provides if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer) {
            Text(text = title, style = MaterialTheme.typography.labelMedium)
        }
    }
}