import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.android.core.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationTopBar(modifier: Modifier = Modifier, title: String) {
    TopAppBar(
        modifier = modifier,
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Rounded.ArrowBack, contentDescription = null)
            }
        }
    )
}