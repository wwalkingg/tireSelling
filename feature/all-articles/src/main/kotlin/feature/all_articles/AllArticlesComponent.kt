package feature.all_articles

import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Article
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.network.api.Apis
import core.network.api.getArticles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AllArticlesComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext {
    internal val modelState = AllArticlesModelState()
}

internal class AllArticlesModelState : ModelState() {
    private val _loadAllArticlesUIStateFlow =
        MutableStateFlow<LoadUIState<Map<String, List<Article>>>>(LoadUIState.Loading)
    val loadAllArticlesUIStateFlow = _loadAllArticlesUIStateFlow.asStateFlow()

    init {
        loadAllArticles()
    }

    fun loadAllArticles() {
        coroutineScope.launch {
            _loadAllArticlesUIStateFlow.value = LoadUIState.Loading
            Apis.Article.getArticles().catch {
                _loadAllArticlesUIStateFlow.value = LoadUIState.Error(it)
            }.collect {
                val map = mutableMapOf<String, List<Article>>()
                map["全部"] = it
                map.putAll(it.groupBy { it.productType })
                _loadAllArticlesUIStateFlow.value = LoadUIState.Loaded(map.toMap())
            }
        }
    }
}
