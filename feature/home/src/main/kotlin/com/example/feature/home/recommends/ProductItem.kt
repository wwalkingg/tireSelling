package com.example.feature.home.recommends

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.android.core.model.Product

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    product: Product,
    onClick: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(2.dp)
) {
    Column(
        modifier
            .clickable { onClick() }
            .padding(contentPadding)) {
        Box(
            Modifier
                .weight(1f)
                .layout { measurable, constraints ->
                    println(constraints)
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(0, 0)
                    }
                }
                .clip(MaterialTheme.shapes.medium)
        ) {
            AsyncImage(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary),
                model = product.image,
                contentDescription = null,
            )
            if(product.isHot) {
                Text(
                    text = "热门",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .background(MaterialTheme.colorScheme.error.copy(.2f))
                        .padding(2.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        Text(
            text = product.name,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "￥ ${product.price}",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.error,
            fontStyle = FontStyle.Italic
        )
    }
}