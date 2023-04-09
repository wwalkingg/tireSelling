import com.arkivanov.decompose.ComponentContext
import core.component_base.ModelState

class AddressManagementComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext {
    internal val modelState = AddressManagementModelState()
}

internal class AddressManagementModelState : ModelState() {
    val address
}
