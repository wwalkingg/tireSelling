import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.router.stack.pop
import core.common.navigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationTopBar(modifier: Modifier = Modifier, title: String) {
    TopAppBar(
        modifier = modifier,
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { navigation.pop() }) {
                Icon(Icons.Rounded.ArrowBack, contentDescription = null)
            }
        }
    )
}