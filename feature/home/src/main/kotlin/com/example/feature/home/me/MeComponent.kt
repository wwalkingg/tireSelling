package com.example.feature.home.me

import core.component_base.ModelState
import com.arkivanov.decompose.ComponentContext

class MeComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val model = MeModelState()
}

internal class MeModelState : ModelState() {

}
