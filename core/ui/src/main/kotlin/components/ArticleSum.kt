package components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.android.core.model.Article
import core.common.Config

@Composable
fun ArticleSum(modifier: Modifier = Modifier, article: Article, onClick: () -> Unit) {
    if (article.images.isEmpty()) {
        ArticleSumWithoutImage(modifier = modifier, article = article, onClick)
    } else {
        ArticleSumWithImage(modifier = modifier, article = article, onClick)
    }
}

@Composable
internal fun ArticleSumWithImage(modifier: Modifier = Modifier, article: Article, onClick: () -> Unit) {
    val mergeModifier = Modifier
        .height(IntrinsicSize.Max)
        .then(modifier)
        .clickable { onClick() }
        .padding(10.dp)
    Row(mergeModifier) {
        AsyncImage(
            model = Config.baseImgUrl + article.images[0],
            contentDescription = null,
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(Color.Gray.copy(.5f))
                .fillMaxHeight()
                .aspectRatio(1.4f),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = article.content, maxLines = 3, overflow = TextOverflow.Ellipsis)
            Text(
                text = "发布于：${article.publishDate}",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray.copy(.8f)
            )
        }
    }
}

@Composable
internal fun ArticleSumWithoutImage(modifier: Modifier = Modifier, article: Article, onClick: () -> Unit) {
    val mergeModifier = Modifier
        .height(IntrinsicSize.Max)
        .then(modifier)
        .clickable { onClick() }
        .padding(10.dp)
    Row(mergeModifier) {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = article.content, maxLines = 3, overflow = TextOverflow.Ellipsis)
            Text(
                text = "发布于：${article.publishDate}",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray.copy(.8f)
            )
        }
    }
}
