import com.arkivanov.decompose.ComponentContext
import core.component_base.ModelState

class CollectionStoreComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = CollectionStoreModelState()
}

internal class CollectionStoreModelState : ModelState() {

}
