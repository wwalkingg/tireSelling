import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun SettingItem(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit,
    icon: (@Composable () -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.background(backgroundColor).clickable { onClick() }.padding(10.dp, 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min).weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.padding(end = 10.dp).requiredSize(iconSize)) {
                Box(modifier = Modifier.align(Alignment.Center)) {
                    if (icon != null) {
                        icon()
                    }
                }
            }
            Box(modifier = Modifier.fillMaxHeight()) {
                Box(modifier = Modifier.align(Alignment.CenterStart)) {
                    CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.titleMedium) {
                        label()
                    }
                }
            }
        }
        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
    }
}

private val iconSize = 24.dp