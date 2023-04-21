package com.example.feature.home.category

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.android.core.model.Product
import core.common.Config

@Composable
fun ProductItemSimple(
    modifier: Modifier = Modifier,
    product: Product,
    onClick: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(2.dp)
) {
    Column(
        modifier.clip(MaterialTheme.shapes.small)
            .clickable { onClick() }
            .padding(contentPadding)) {
        Box(
            Modifier
                .weight(1f)
                .clip(MaterialTheme.shapes.medium)
        ) {
            AsyncImage(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary),
                model = Config.baseImgUrl+product.image,
                contentDescription = null, contentScale = ContentScale.Crop
            )
            if (product.isHot) {
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
    }
}