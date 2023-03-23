package com.example.android.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import core.common.navigation.Config
import core.common.navigation.rootNavigation
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


class RootComponent(componentContext: ComponentContext) : ComponentContext by componentContext {

    private val _childStack =
        childStack(
            source = rootNavigation,
            initialConfiguration = Config.RootConfig.Home,
            handleBackButton = true,
            childFactory = ::createChild,
        )
    internal val stack: Value<ChildStack<Config.RootConfig, Child>> = _childStack
    private fun createChild(config: Config.RootConfig, componentContext: ComponentContext): Child {
        return when (config) {
            is Config.RootConfig.Home -> Child.Home(HomeComponent(componentContext))
            Config.RootConfig.CoachAll -> Child.CoachAll(CoachAllComponent(componentContext))
            Config.RootConfig.CoachDetail -> Child.CoachDetail(CoachDetailComponent(componentContext))
            Config.RootConfig.CourseAll -> Child.CourseAll(CourseAllComponent(componentContext))
            Config.RootConfig.CourseDetail -> Child.CourseDetail(
                CourseDetailComponent(
                    componentContext
                )
            )

            Config.RootConfig.Login -> Child.Login(LoginComponent(componentContext))
            Config.RootConfig.PartnerFind -> Child.PartnerFind(PartnerFindComponent(componentContext))
            Config.RootConfig.PersonHealth -> Child.PersonHealth(
                PersonHealthComponent(
                    componentContext
                )
            )

            Config.RootConfig.Register -> Child.Register(RegisterComponent(componentContext))
            Config.RootConfig.UserInfoModifier -> Child.UserInfoModifier(
                UserInfoModifierComponent(
                    componentContext
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

}
