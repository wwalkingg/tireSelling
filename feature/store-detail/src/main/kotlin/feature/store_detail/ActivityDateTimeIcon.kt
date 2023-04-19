package feature.store_detail

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill

@Composable
fun ActivityDateTimeIcon(modifier: Modifier) {

    Canvas(modifier) {
        val radius = 8f
        // 生成路径
        val path = Path().apply {
            moveTo(size.width / 2, 2 * radius)
//            lineTo(size.width / 2, size.height - 2 * radius)
            addRect(
                Rect(
                    left = size.width / 2 - 2,
                    top = 2 * radius,
                    right = size.width / 2 + 2,
                    bottom = size.height - 2 * radius
                )
            )
            addOval(
                Rect(
                    left = size.width / 2 - radius,
                    top = 2 * radius,
                    right = size.width / 2 + radius,
                    bottom = 2 * radius + 2 * radius
                )
            )
            addOval(
                Rect(
                    left = size.width / 2 - radius,
                    top = size.height - 2 * radius - 2 * radius,
                    right = size.width / 2 + radius,
                    bottom = size.height - 2 * radius
                )
            )
        }

        // 绘制路径
        drawPath(
            path = path,
            brush = Brush.verticalGradient(listOf(Color.Yellow, Color.Green)),
            style = Fill
        )
    }
}