package components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.android.core.model.Product
import core.common.Config

@Composable
fun ProductItem(modifier: Modifier = Modifier, product: Product) {
    val mergeModifier = modifier.padding(10.dp)
    Column(mergeModifier) {
        Row(modifier = Modifier.height(IntrinsicSize.Max)) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = product.productDescription,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "${product.brand} - ${product.model}", maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            AsyncImage(
                model = Config.baseImgUrl + product.image, contentDescription = null, modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .background(Color.Gray.copy(.5f))
            )
        }
    }
}