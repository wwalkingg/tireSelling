package core.ui.status_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ErrorPage(modifier: Modifier = Modifier, errorMsg: String = "出错啦", onRefreshClick: () -> Unit) {
    Surface(modifier, color = MaterialTheme.colorScheme.errorContainer) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(errorMsg)
            TextButton(onClick = onRefreshClick) {
                Text("刷新")
            }
        }
    }
}