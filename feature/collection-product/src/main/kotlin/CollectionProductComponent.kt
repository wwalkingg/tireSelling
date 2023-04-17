import com.arkivanov.decompose.ComponentContext
import core.component_base.ModelState

class CollectionProductComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = CollectionProductModelState()
}

internal class CollectionProductModelState : ModelState() {

}
