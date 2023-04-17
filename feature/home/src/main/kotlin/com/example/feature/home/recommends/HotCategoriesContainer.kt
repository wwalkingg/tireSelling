package com.example.feature.home.recommends

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.android.core.model.Category
import com.example.core.design_system.Icons
import com.example.feature.home.category.CategoryItem
import kotlinx.collections.immutable.PersistentList

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HotCategoriesContainer(
    modifier: Modifier = Modifier,
    categories: PersistentList<Category>,
    onCategoryClick: (category: Category) -> Unit
) {
    Column(
        modifier
            .padding(10.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.primaryContainer)
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
                color = MaterialTheme.colorScheme.error,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Black
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        // content
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimaryContainer) {
            FlowRow(modifier = Modifier.fillMaxWidth(), maxItemsInEachRow = 5) {
                categories.forEach { category ->
                    CategoryItem(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .weight(1f),
                        contentPadding = PaddingValues(2.dp),
                        category = category,
                        onClick = { onCategoryClick(category) }
                    )
                }
            }
        }

    }
}