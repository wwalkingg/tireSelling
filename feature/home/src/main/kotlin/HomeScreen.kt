import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry

@Composable
fun HomeScreen(modifier: Modifier = Modifier, component: HomeComponent) {
    val rootSnackBarHostState = LocalRootSnackBarHostState.current
}
