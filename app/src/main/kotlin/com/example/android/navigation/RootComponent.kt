package com.example.android.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import core.common.navigation.ComponentContextWithNavigation
import core.common.navigation.Config
import core.common.navigation.withNavigation
import feature.all_course.CourseAllComponent
import feature.auth.LoginComponent
import feature.auth.PasswordModifierComponent
import feature.auth.RegisterComponent
import feature.auth.UserInfoModifierComponent
import feature.coach_detail.CoachDetailComponent
import feature.course_all.CoachAllComponent
import feature.course_detail.CourseDetailComponent
import feature.home.HomeComponent
import feature.partner_find.PartnerFindComponent
import feature.person_health.PersonHealthComponent

internal val navigation = StackNavigation<RootComponent.RootConfig>()

class RootComponent(componentContext: ComponentContext) : ComponentContext by componentContext {

    private val _childStack =
        childStack(
            source = navigation,
            initialConfiguration = RootConfig.Home,
            handleBackButton = true,
            childFactory = ::createChild,
        )
    internal val stack: Value<ChildStack<RootConfig, Child>> = _childStack
    private fun createChild(config: RootConfig, componentContext: ComponentContext): Child {
        val componentContextWithNavigation =
            componentContext.withNavigation(parentNavigation = navigation, config = config)
        return when (config) {
            is RootConfig.Home -> Child.Home(HomeComponent(componentContextWithNavigation))
            RootConfig.CoachAll -> Child.CoachAll(CoachAllComponent(componentContextWithNavigation))
            RootConfig.CoachDetail -> Child.CoachDetail(CoachDetailComponent(componentContextWithNavigation))
            RootConfig.CourseAll -> Child.CourseAll(CourseAllComponent(componentContextWithNavigation))
            RootConfig.CourseDetail -> Child.CourseDetail(CourseDetailComponent(componentContextWithNavigation))
            RootConfig.Login -> Child.Login(LoginComponent(componentContextWithNavigation))
            RootConfig.PartnerFind -> Child.PartnerFind(PartnerFindComponent(componentContextWithNavigation))
            RootConfig.PersonHealth -> Child.PersonHealth(PersonHealthComponent(componentContextWithNavigation))
            RootConfig.Register -> Child.Register(RegisterComponent(componentContextWithNavigation))
            RootConfig.UserInfoModifier -> Child.UserInfoModifier(
                UserInfoModifierComponent(
                    componentContextWithNavigation
                )
            )
        }
    }


    internal sealed interface Child {
        data class Home(val component: HomeComponent) : Child
        data class CourseAll(val component: CourseAllComponent) : Child
        data class CourseDetail(val component: CourseDetailComponent) : Child
        data class CoachAll(val component: CoachAllComponent) : Child
        data class CoachDetail(val component: CoachDetailComponent) : Child
        data class PartnerFind(val component: PartnerFindComponent) : Child
        data class PersonHealth(val component: PersonHealthComponent) : Child
        data class Login(val component: LoginComponent) : Child
        data class Register(val component: RegisterComponent) : Child
        data class UserInfoModifier(val component: UserInfoModifierComponent) : Child
        data class PasswordModifier(val component: PasswordModifierComponent) : Child
    }

    internal sealed interface RootConfig : Parcelable, Config {
        @Parcelize
        object Home : RootConfig

        @Parcelize
        object CourseAll : RootConfig

        @Parcelize
        object CourseDetail : RootConfig

        @Parcelize
        object CoachAll : RootConfig

        @Parcelize
        object CoachDetail : RootConfig

        @Parcelize
        object PartnerFind : RootConfig

        @Parcelize
        object PersonHealth : RootConfig

        @Parcelize
        object UserInfoModifier : RootConfig

        @Parcelize
        object Register : RootConfig

        @Parcelize
        object Login : RootConfig
    }

}
