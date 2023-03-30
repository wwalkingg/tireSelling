package com.example.feature.home.me

import ModelState
import com.arkivanov.decompose.ComponentContext

class MeComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val model = MeModelState()
}

internal class MeModelState : ModelState() {

}
