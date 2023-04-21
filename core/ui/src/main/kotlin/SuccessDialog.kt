import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay

@Composable
fun SuccessDialog() {
    var isVisible by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(2000L)
        isVisible = false
    }
    if (isVisible) {
        Dialog(onDismissRequest = {}){
            Box(
                modifier = Modifier.clip(MaterialTheme.shapes.extraLarge).fillMaxWidth(.8f).aspectRatio(1f)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Icon(
                    Icons.Default.Check, contentDescription = null, tint = Color.Green, modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        }
    }
}