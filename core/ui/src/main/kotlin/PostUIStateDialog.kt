import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import core.component_base.PostUIState
import kotlinx.coroutines.delay

@Composable
fun PostUIStateDialog(
    modifier: Modifier = Modifier,
    postUIState: PostUIState,
    dialogStayTime: Long = 1300L,
    loading: @Composable () -> Unit = {
        CircularProgressIndicator()
    },
    error: @Composable (throwable: Throwable) -> Unit = {
        Icon(imageVector = Icons.Default.Error, contentDescription = null,tint = Color.Red)
    },
    success: @Composable () -> Unit = {
        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.Green)
    }
) {
    when (postUIState) {
        is PostUIState.Error -> DialogContent(modifier) {
            var isVisible by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(Unit) {
                isVisible = true
                delay(dialogStayTime)
                isVisible = false
            }
            if (isVisible)
                DialogContent(modifier) {
                    error(postUIState.error)
                }

        }

        PostUIState.Loading -> {
            loading()
        }

        PostUIState.None -> {}
        PostUIState.Success -> {
            var isVisible by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(Unit) {
                isVisible = true
                delay(dialogStayTime)
                isVisible = false
            }
            if (isVisible)
                DialogContent(modifier) {
                    success()
                }

        }
    }
}
