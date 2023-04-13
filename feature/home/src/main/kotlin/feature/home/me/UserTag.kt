import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun UserTag(modifier: Modifier = Modifier, text: String, color: Color = MaterialTheme.colorScheme.primaryContainer) {
    Text(
        text,
        modifier = modifier.clip(RoundedCornerShape(4.dp)).background(color).padding(4.dp, 2.dp),
        style = MaterialTheme.typography.labelSmall
    )
}