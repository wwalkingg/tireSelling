package com.example.feature.home.recommends

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.android.core.model.Category
import com.example.core.design_system.Icons
import com.example.feature.home.category.CategoryItem
import kotlinx.collections.immutable.PersistentList

@Composable
fun HotCategoriesContainer(modifier: Modifier = Modifier, categories: PersistentList<Category>) {
    Column(
        modifier
            .padding(10.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(10.dp)
    ) {
        // title
        Row(
            modifier = Modifier.padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = Icons.hotInfo),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                text = "热门分类",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
        // content
        LazyVerticalGrid(
            modifier = Modifier,
            columns = GridCells.Fixed(5),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(items = categories, key = { it.id }) {
                CategoryItem(
                    modifier = Modifier.height(70.dp),
                    category = it,
                    onClick = {},
                    contentPadding = PaddingValues(2.dp)
                )
            }
        }

    }
}