package feature.article_detail

import LoadUIStateScaffold
import NavigationTopBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.android.core.model.Article
import core.common.Config

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    modifier: Modifier = Modifier,
    component: ArticleDetailComponent
) {
    val loadArticleDetailUIState by component.modelState.loadArticleDetailUIStateFlow.collectAsState()
    LoadUIStateScaffold(loadUIState = loadArticleDetailUIState) { article: Article ->
        Scaffold(
            topBar = {
                NavigationTopBar(title = "")
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = article.title, style = MaterialTheme.typography.titleLarge)
                Text(text = article.content, style = MaterialTheme.typography.bodyMedium)
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    article.images.forEach {
                        AsyncImage(
                            model = Config.baseImgUrl + it,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.5f)
                                .background(Color.Gray.copy(.4f)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}