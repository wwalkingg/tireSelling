import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

class HomeComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    val model = HomeModelState()

    internal sealed interface Child {
//        data class Recommend(val component: RecommendComponent) : Child
    }

    internal sealed interface Config : Parcelable {
        @Parcelize
        object Recommend : Config

        @Parcelize
        object Plan : Config

        @Parcelize
        object Statistic : Config

        @Parcelize
        object Me : Config
    }
}