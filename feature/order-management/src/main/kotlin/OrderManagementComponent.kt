import com.arkivanov.decompose.ComponentContext
import core.component_base.ModelState

class OrderManagementComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = OrderManagementModelState()
}

internal class OrderManagementModelState : ModelState() {

}
