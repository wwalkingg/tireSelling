import com.arkivanov.decompose.ComponentContext
import core.component_base.ModelState

class RewardPointsComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = RewardPointsModelState()
}

internal class RewardPointsModelState : ModelState() {

}
