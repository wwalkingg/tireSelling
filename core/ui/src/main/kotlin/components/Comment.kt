package components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    avatar: String,
    username: String,
    content: String,
    datetime: String
) {
    Column(modifier) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = avatar,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(16.dp)
                    .background(MaterialTheme.colorScheme.outlineVariant)
            )
            Text(
                text = username,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = content, style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = datetime,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Preview
@Composable
private fun CommentPreview() {
    Comment(
        modifier = Modifier.fillMaxWidth(),
        avatar = "",
        username = "test",
        content = "test",
        datetime = "2021-10-10 10:10:10"
    )
}