package com.example.android.navigation

import HomeComponent
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

internal val navigation = StackNavigation<RootComponent.Config>()

class RootComponent(componentContext: ComponentContext) : ComponentContext by componentContext {

    private val _childStack =
        childStack(
            source = navigation,
            initialConfiguration = Config.Home,
            handleBackButton = true,
            childFactory = ::createChild,
        )

    internal val stack: Value<ChildStack<Config, Child>> = _childStack

    private fun createChild(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.Home -> Child.Home(HomeComponent(componentContext))
        }

    internal sealed class Config : Parcelable {
        @Parcelize
        object Home : Config()
    }

    internal sealed interface Child {
        data class Home(val component: HomeComponent) : Child
    }
}
