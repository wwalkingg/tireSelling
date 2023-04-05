package core.common

import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.parcelize.Parcelize

val navigation = StackNavigation<NavConfig>()

sealed class NavConfig : Parcelable {
    @Parcelize
    object Home : NavConfig()

    @Parcelize
    object AllArticles : NavConfig()

    @Parcelize
    data class ArticleDetail(val id: Int, val title: String? = null) : NavConfig()

    @Parcelize
    data class ProductDetail(val id: Int, val title: String? = null, val image: String? = null) :
        NavConfig()

    @Parcelize
    object AddressManagement : NavConfig()

    @Parcelize
    object CollectionProduct : NavConfig()

    @Parcelize
    object CollectionStore : NavConfig()


    @Parcelize
    object RewardPoints : NavConfig()

    @Parcelize
    object OrderManagement : NavConfig()

    @Parcelize
    object Login : NavConfig()


}