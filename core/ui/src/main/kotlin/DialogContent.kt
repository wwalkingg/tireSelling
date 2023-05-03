import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog

@Composable
fun DialogContent(content: @Composable () -> Unit) {
    Dialog(onDismissRequest = {}) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}