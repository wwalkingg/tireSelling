package feature.article_detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Article
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.network.api.getArticleDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ArticleDetailComponent(componentContext: ComponentContext, articleId: Int, title: String?) :
    ComponentContext by componentContext {
    internal val modelState = ArticleDetailModelState(articleId, title)
}

internal class ArticleDetailModelState(val id: Int, title: String? = null) : ModelState() {
    var title by mutableStateOf(title ?: "文章详情")

    private val _loadArticleDetailUIStateFlow =
        MutableStateFlow<LoadUIState<Article>>(LoadUIState.Loading)
    val loadArticleDetailUIStateFlow = _loadArticleDetailUIStateFlow.asStateFlow()

    init {
        loadArticleDetail(id)
    }

    fun loadArticleDetail(id: Int) {
        coroutineScope.launch {
            _loadArticleDetailUIStateFlow.emit(LoadUIState.Loading)
            getArticleDetail(id).catch {
                _loadArticleDetailUIStateFlow.emit(LoadUIState.Error(it))
                Log.e("ArticleDetail", "loadArticleDetail: $it")
            }.collect {
                title = it.title
                _loadArticleDetailUIStateFlow.emit(LoadUIState.Loaded(it))
            }
        }
    }

}
