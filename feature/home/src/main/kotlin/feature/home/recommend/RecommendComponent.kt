package feature.home.recommend

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.push
import core.common.navigation.Config
import core.common.navigation.rootNavigation

class RecommendComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext {

    internal fun onCourseAllFunctionalMenuClick() {
        rootNavigation.push(Config.RootConfig.CourseAll)
    }

    internal fun onCourseDetailFunctionalMenuClick() {
        rootNavigation.push(Config.RootConfig.CoachDetail)
    }

    internal fun onPersonHealthFunctionalMenuClick() {
        rootNavigation.push(Config.RootConfig.PersonHealth)
    }

    internal fun onCoachAllFunctionalMenuClick() {
        rootNavigation.push(Config.RootConfig.CoachAll)
    }

    internal fun onPartnerFindFunctionalMenuClick() {
        rootNavigation.push(Config.RootConfig.PartnerFind)
    }

}