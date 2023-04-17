package com.example.feature.home.recommends

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.android.core.model.Product
import kotlinx.collections.immutable.PersistentList

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HotProductsFlowContainer(
    modifier: Modifier = Modifier,
    products: PersistentList<Product>,
    onProductClick: (Product) -> Unit
) {
    FlowRow(modifier, maxItemsInEachRow = 3) {
        products.forEach { product ->
            ProductItem(
                modifier = Modifier
                    .padding(2.dp)
                    .aspectRatio(0.618f)
                    .weight(1f, false)
                    .clip(MaterialTheme.shapes.small),
                product = product,
                onClick = { onProductClick(product) }
            )
        }
    }
}