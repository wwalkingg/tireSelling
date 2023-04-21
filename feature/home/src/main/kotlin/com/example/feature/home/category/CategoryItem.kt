package com.example.feature.home.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.android.core.model.Category
import core.common.Config

@Composable
fun CategoryItem(
    modifier: Modifier,
    category: Category,
    onClick: () -> Unit,
    imgBackground: Color = MaterialTheme.colorScheme.primary,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(imgBackground)
                .aspectRatio(1f),
            model = Config.baseImgUrl+category.categoryImg, contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            text = category.name,
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}