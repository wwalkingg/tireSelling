package com.example.android.navigation

import SearchScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import feature.all_course.CourseAllScreen
import feature.auth.LoginScreen
import feature.auth.PasswordModifierScreen
import feature.auth.RegisterScreen
import feature.auth.UserInfoModifierScreen
import feature.coach_detail.CoachDetailScreen
import feature.course_all.CoachAllScreen
import feature.course_detail.CourseDetailScreen
import feature.home.HomeScreen
import feature.my_collect.MyCollectScreen
import feature.my_subscribe.MySubscribeScreen
import feature.partner_find.PartnerFindScreen
import feature.person_health.PersonHealthScreen

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(slide()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Home -> HomeScreen(Modifier.fillMaxSize(), child.component)
            is RootComponent.Child.CoachAll -> CoachAllScreen(Modifier.fillMaxSize(), child.component)
            is RootComponent.Child.CoachDetail -> CoachDetailScreen(Modifier.fillMaxSize(), child.component)
            is RootComponent.Child.CourseAll -> CourseAllScreen(Modifier.fillMaxSize(), child.component)
            is RootComponent.Child.CourseDetail -> CourseDetailScreen(Modifier.fillMaxSize(), child.component)
            is RootComponent.Child.PartnerFind -> PartnerFindScreen(Modifier.fillMaxSize(), child.component)
            is RootComponent.Child.PersonHealth -> PersonHealthScreen(Modifier.fillMaxSize(), child.component)
            is RootComponent.Child.PasswordModifier -> PasswordModifierScreen(Modifier.fillMaxSize(), child.component)
            is RootComponent.Child.Login -> LoginScreen(Modifier.fillMaxSize(), child.component)
            is RootComponent.Child.Register -> RegisterScreen(Modifier.fillMaxSize(), child.component)
            is RootComponent.Child.UserInfoModifier -> UserInfoModifierScreen(Modifier.fillMaxSize(), child.component)
            is RootComponent.Child.MyCollect -> MyCollectScreen(Modifier.fillMaxSize(), child.component)
            is RootComponent.Child.MySubscribe -> MySubscribeScreen(Modifier.fillMaxSize(), child.component)
            is RootComponent.Child.Search -> SearchScreen(Modifier.fillMaxSize(), child.component)
        }
    }
}