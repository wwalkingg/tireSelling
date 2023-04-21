package com.example.android.navigation

import AddressManagementComponent
import CollectionProductComponent
import LoginComponent
import OrderManagementComponent
import RewardPointsComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.example.feature.home.HomeComponent
import core.common.NavConfig
import core.common.navigation
import feature.all_articles.AllArticlesComponent
import feature.article_detail.ArticleDetailComponent
import feature.modifier_userinfo.ModifierUserinfoComponent
import feature.product_detail.ProductDetailComponent
import feature.store_detail.StoreDetailComponent

class RootComponent(componentContext: ComponentContext) : ComponentContext by componentContext {

    private val _childStack =
        childStack(
            source = navigation,
            initialConfiguration = NavConfig.Home,
            handleBackButton = true,
            childFactory = ::createChild,
        )

    internal val stack: Value<ChildStack<NavConfig, Child>> = _childStack

    private fun createChild(config: NavConfig, componentContext: ComponentContext): Child =
        when (config) {
            is NavConfig.Home -> Child.Home(HomeComponent(componentContext))
            NavConfig.AllArticles -> Child.AllArticles(AllArticlesComponent(componentContext))
            is NavConfig.ArticleDetail -> Child.ArticleDetail(
                ArticleDetailComponent(
                    componentContext,
                    config.id,
                    config.title
                )
            )

            is NavConfig.ProductDetail -> Child.ProductDetail(
                ProductDetailComponent(
                    componentContext,
                    config.id
                )
            )

            NavConfig.AddressManagement -> Child.AddressManagement(
                AddressManagementComponent(
                    componentContext
                )
            )

            NavConfig.CollectionProduct -> Child.CollectionProduct(
                CollectionProductComponent(
                    componentContext
                )
            )

            NavConfig.ModifierUserinfo -> Child.ModifierUserinfo(
                ModifierUserinfoComponent(
                    componentContext
                )
            )

            NavConfig.Login -> Child.Login(LoginComponent(componentContext))
            NavConfig.OrderManagement -> Child.OrderManagement(
                OrderManagementComponent(
                    componentContext
                )
            )

            NavConfig.RewardPoints -> Child.RewardPoints(RewardPointsComponent(componentContext))
            is NavConfig.StoreDetail -> Child.StoreDetail(StoreDetailComponent(componentContext, config.id))
        }


    internal sealed interface Child {
        data class Home(val component: HomeComponent) : Child
        data class AllArticles(val component: AllArticlesComponent) : Child
        data class ArticleDetail(val component: ArticleDetailComponent) : Child
        data class ProductDetail(val component: ProductDetailComponent) : Child

        data class AddressManagement(val component: AddressManagementComponent) : Child
        data class CollectionProduct(val component: CollectionProductComponent) : Child
        data class ModifierUserinfo(val component: ModifierUserinfoComponent) : Child
        data class Login(val component: LoginComponent) : Child
        data class OrderManagement(val component: OrderManagementComponent) : Child
        data class RewardPoints(val component: RewardPointsComponent) : Child
        data class StoreDetail(val component: StoreDetailComponent) : Child
    }
}
