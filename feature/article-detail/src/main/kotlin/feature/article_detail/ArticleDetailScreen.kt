package feature.article_detail

import NavigationTopBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.component_base.LoadUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    modifier: Modifier = Modifier,
    component: ArticleDetailComponent
) {
    Scaffold(topBar = { NavigationTopBar(title = component.modelState.title) }) { padding ->
        val loadArticleDetailUIState by component.modelState.loadArticleDetailUIStateFlow.collectAsState()
        when (loadArticleDetailUIState) {
            is LoadUIState.Error -> {}
            is LoadUIState.Loaded -> {
                val articleDetail = (loadArticleDetailUIState as LoadUIState.Loaded).data
                Column(Modifier.padding(padding)) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        AssistChip(
                            onClick = { },
                            label = { Text(text = articleDetail.productType) })
                        Text(
                            text = articleDetail.publishDate,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    Text(
                        text = articleDetail.content,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }

            LoadUIState.Loading -> Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
    }
}