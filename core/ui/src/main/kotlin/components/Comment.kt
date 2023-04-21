package components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import core.common.Config

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Comment(
    modifier: Modifier = Modifier, avatar: String, username: String, content: String, datetime: String
) {
    Column(modifier) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = Config.baseImgUrl+avatar,
                contentDescription = null,
                modifier = Modifier.clip(CircleShape)
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .background(MaterialTheme.colorScheme.outlineVariant), contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = content, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.basicMarquee()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = username, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = datetime,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
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