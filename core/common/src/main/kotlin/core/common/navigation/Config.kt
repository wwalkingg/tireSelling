package core.common.navigation

import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.parcelize.Parcelize

interface Config : Parcelable {
    sealed interface RootConfig : Config {
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