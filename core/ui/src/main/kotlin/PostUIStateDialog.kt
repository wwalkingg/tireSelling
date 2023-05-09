import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
        Icon(imageVector = Icons.Default.Error, contentDescription = null, tint = Color.Red)
    },
    success: @Composable (String) -> Unit = {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.Green)
            Text(text = it)
        }
    }
) {
    when (postUIState) {
        is PostUIState.Error -> DialogContent(modifier) {
            DialogContent(modifier) {
                error(postUIState.error)
            }
        }

        PostUIState.Loading -> DialogContent {
            loading()
        }

        PostUIState.None -> {}

        is PostUIState.Success -> {
            DialogContent(modifier) {
                success(postUIState.message)
            }
        }
    }
}
