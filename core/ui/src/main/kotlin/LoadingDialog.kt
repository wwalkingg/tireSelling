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
import androidx.compose.ui.window.Dialog

@Composable
fun LoadingDialog() {
    Dialog(onDismissRequest = {}) {
        Box(
            modifier = Modifier.clip(MaterialTheme.shapes.extraLarge).fillMaxWidth(.8f)
                .aspectRatio(1f)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}