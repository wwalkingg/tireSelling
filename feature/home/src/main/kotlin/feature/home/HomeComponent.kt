package feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import core.design_system.Icons
import feature.home.me.MeComponent
import feature.home.recommend.RecommendComponent
import feature.home.statistic.StatisticComponent
import feature.my_subscribe.MySubscribeComponent

class HomeComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext {
    internal val model = instanceKeeper.getOrCreate {
        HomeModelState()
    }

    val recommendComponent = RecommendComponent(componentContext.childContext("recommendComponent"))
    val meComponent = MeComponent(componentContext.childContext("meComponent"))
    val statisticComponent = StatisticComponent(componentContext.childContext("statisticComponent"))
    val myScripeComponent = MySubscribeComponent(componentContext.childContext("myScripeComponent"))

    @OptIn(ExperimentalFoundationApi::class)
    val pagerState = PagerState(0)
}

internal enum class BottomMenu(
    val title: String,
    val icon: Int,
    val iconSelected: Int
) {
    Recommend("推荐", Icons.gift, Icons.giftDuotone),
    Plan("计划", Icons.calendar, Icons.calendarDuotone),
    Person("个人", Icons.userFocus, Icons.userFocusDuotone),
    Statistics("统计", Icons.chartLine, Icons.chartLineDuotone),
    User("我的", Icons.userFocus, Icons.userFocusDuotone),
}