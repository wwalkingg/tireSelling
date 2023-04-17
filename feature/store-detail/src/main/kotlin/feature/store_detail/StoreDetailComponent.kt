package feature.store_detail

import com.arkivanov.decompose.ComponentContext
import core.component_base.ModelState

class StoreDetailComponent(componentContext: ComponentContext, val id: Int) :
    ComponentContext by componentContext {
    internal val modelState = StoreDetailModelState(id)
}

internal class StoreDetailModelState(val id: Int) : ModelState() {

}