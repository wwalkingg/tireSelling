package core.ui.status_page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.design_system.Icons


@Composable
fun EmptyBox(modifier: Modifier = Modifier, message: String) {
    Box(modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painterResource(Icons.emptyBox),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(message)
        }
    }
}


@Preview(widthDp = 500, heightDp = 500)
@Composable
private fun EmptyBoxPreview() {
    EmptyBox(modifier = Modifier.fillMaxSize(), message = "Empty")
}