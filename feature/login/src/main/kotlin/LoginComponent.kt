import com.arkivanov.decompose.ComponentContext
import core.component_base.ModelState

class LoginComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = LoginModelState()
}

internal class LoginModelState : ModelState() {

}
