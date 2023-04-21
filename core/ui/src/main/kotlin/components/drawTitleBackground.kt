package components

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush

fun Modifier.drawTitleBackground(): Modifier = composed {
    val gray = MaterialTheme.colorScheme.outlineVariant
    this.drawBehind {
        drawRect(
            brush = Brush.verticalGradient(
                listOf(
                    gray.copy(0f),
                    gray
                )
            )
        )
    }
}