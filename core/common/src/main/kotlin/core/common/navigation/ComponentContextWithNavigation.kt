package core.common.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation

class ComponentContextWithNavigation(
    val componentContext: ComponentContext,
    val parentNavigation: StackNavigation<out Config>,
    val config: Config
) : ComponentContext by componentContext

fun ComponentContext.withNavigation(
    parentNavigation: StackNavigation<out Config>,
    config: Config
) = ComponentContextWithNavigation(this, parentNavigation, config)