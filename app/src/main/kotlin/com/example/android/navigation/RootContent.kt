package com.example.android.navigation

import AddressManagementScreen
import BrandDetailScreen
import CollectionProductScreen
import CouponCenterScreen
import LoginScreen
import ModelDetailScreen
import OrderManagementScreen
import RewardPointsScreen
import SearchResultScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.example.feature.home.HomeScreen
import feature.all_articles.AllArticlesScreen
import feature.article_detail.ArticleDetailScreen
import feature.modifier_userinfo.ModifierUserinfoScreen
import feature.product_detail.ProductDetailScreen
import feature.store_detail.StoreDetailScreen

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(slide()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Home -> HomeScreen(Modifier.fillMaxSize(), child.component)
            is RootComponent.Child.AddressManagement -> AddressManagementScreen(component = child.component)
            is RootComponent.Child.AllArticles -> AllArticlesScreen(component = child.component)
            is RootComponent.Child.ArticleDetail -> ArticleDetailScreen(component = child.component)
            is RootComponent.Child.CollectionProduct -> CollectionProductScreen(component = child.component)
            is RootComponent.Child.ModifierUserinfo -> ModifierUserinfoScreen(component = child.component)
            is RootComponent.Child.Login -> LoginScreen(component = child.component)
            is RootComponent.Child.OrderManagement -> OrderManagementScreen(component = child.component)
            is RootComponent.Child.ProductDetail -> ProductDetailScreen(component = child.component)
            is RootComponent.Child.RewardPoints -> RewardPointsScreen(component = child.component)
            is RootComponent.Child.StoreDetail -> StoreDetailScreen(component = child.component)
            is RootComponent.Child.CouponCenter -> CouponCenterScreen(component = child.component)
            is RootComponent.Child.BrandDetail -> BrandDetailScreen(component = child.component)
            is RootComponent.Child.ModelDetail ->ModelDetailScreen(component = child.component)
            is RootComponent.Child.SearchResult -> SearchResultScreen(component = child.component)
        }
    }
}