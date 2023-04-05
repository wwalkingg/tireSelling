package com.example.feature.home.recommends

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.android.core.model.Article
import kotlinx.collections.immutable.PersistentList

@Composable
fun HotArticlesContainer(
    modifier: Modifier = Modifier,
    articles: PersistentList<Article>,
    onArticleClick: (article: Article) -> Unit,
    onMoreClick: () -> Unit
) {
    Column(
        modifier
            .padding(10.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
    ) {
        // title
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = "农技咨询",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Black
                )
            }
            AssistChip(onClick = onMoreClick, label = { Text(text = "更多") })
        }
        Spacer(modifier = Modifier.height(5.dp))
        // content
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimaryContainer) {
            Column(
                Modifier
                    .padding(start = 10.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                articles.forEachIndexed { index, article ->
                    Row(
                        modifier = Modifier
                            .height(IntrinsicSize.Max)
                            .clickable { onArticleClick(article) },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "${index + 1}. ${article.title}",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}