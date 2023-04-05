import com.arkivanov.decompose.ComponentContext
import core.component_base.ModelState

class ProductDetailComponent(componentContext: ComponentContext, id: Int, title: String?) : ComponentContext by componentContext {
    internal val modelState = ProductDetailModelState()
}

internal class ProductDetailModelState : ModelState() {

}
