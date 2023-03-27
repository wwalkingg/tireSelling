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
        data class CourseDetail(val id: Int) : RootConfig

        @Parcelize
        object CoachAll : RootConfig

        @Parcelize
        data class CoachDetail(val id: Int) : RootConfig

        @Parcelize
        object PartnerFind : RootConfig

        @Parcelize
        object PersonHealth : RootConfig

        @Parcelize
        object UserInfoModifier : RootConfig

        @Parcelize
        object PasswordModifier : RootConfig

        @Parcelize
        object Register : RootConfig

        @Parcelize
        object Login : RootConfig

        @Parcelize
        object MyCollect : RootConfig

        @Parcelize
        object MySubscribe : RootConfig

        @Parcelize
        object Search : RootConfig
    }

}