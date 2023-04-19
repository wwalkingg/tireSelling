import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import core.component_base.LoadUIState

@Composable
fun <T> SmallLoadUIStateScaffold(
    loadUIState: LoadUIState<T>,
    modifier: Modifier = Modifier,
    successContent: @Composable (T) -> Unit
) {
    Box(modifier) {
        when (loadUIState) {
            is LoadUIState.Error -> {
                val error = loadUIState.error
                error.message?.let { message ->
                    Row(
                        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.errorContainer),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onErrorContainer) {
                            Text(message, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            LoadUIState.Loading -> {
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(vertical = 20.dp)
                ) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }

            is LoadUIState.Loaded -> {
                val data = loadUIState.data
                var isVisible by remember { mutableStateOf(true) }
                if (data is Collection<*>) {
                    isVisible = data.isNotEmpty()
                }
                if (isVisible) {
                    successContent(data)
                }
            }
        }
    }
}