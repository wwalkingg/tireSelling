package feature.article_detail

import com.arkivanov.decompose.ComponentContext
import core.component_base.ModelState

class ArticleDetailComponent(componentContext: ComponentContext, articleId: Any?, title: String?) : ComponentContext by componentContext {
    internal val modelState = ArticleDetailModelState()
}

internal class ArticleDetailModelState : ModelState() {

}
