package components.lineChart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList

@Composable
fun <T> LineChart(
    modifier: Modifier = Modifier,
    xLabel: @Composable () -> Unit,
    yLabel: @Composable () -> Unit,
    xMaxValue: Float,
    yMaxValue: Float,
    colors: (Float) -> Color = { Red },
    xSpacing: Dp = 20.dp,
    data: PersistentList<Pair<T, Float>>
) {
    BoxWithConstraints(modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
        val height = with(LocalDensity.current) { maxHeight.toPx() }
        val stateHorizontal = rememberScrollState(0)
        val xSpacingPixel = with(LocalDensity.current) {
            xSpacing.toPx()
        }
        val path = remember(data) { Path() }.apply { moveTo(0f, height) }
        for (index in data.indices) {
            path.lineTo(index * xSpacingPixel, height - (height / yMaxValue) * data[index].second)
        }
        Canvas(
            modifier = Modifier
                .horizontalScroll(stateHorizontal)
                .fillMaxSize()
                .width(xSpacing * data.size)

        ) {
            data.forEachIndexed { index, pair ->
                val y = size.height - (size.height / yMaxValue) * pair.second
                drawCircle(
                    colors(pair.second),
                    radius = 2f,
                    center = Offset(index * xSpacingPixel, y)
                )
            }
            drawPath(path, Color.Black, style = Stroke(1f))
        }
    }
}