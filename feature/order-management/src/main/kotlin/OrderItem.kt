import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.android.core.model.Order
import core.common.Config

@Composable
fun OrderItem(modifier: Modifier = Modifier, order: Order) {
    Surface(
        modifier,
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Max), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            AsyncImage(
                model = Config.baseImgUrl+order.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth(.2f)
            )
            Column(modifier = Modifier.weight(1f).fillMaxHeight().padding(vertical = 3.dp)) {
                Text(order.name, style = MaterialTheme.typography.titleLarge)
                Text(
                    String.format("CNY %.2f", order.price),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
                Text(order.createTime, style = MaterialTheme.typography.labelSmall)
            }
//            IconButton(onClick = {}){}
        }
    }
}