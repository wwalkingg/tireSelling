package feature.home

import HomeModelState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.push
import core.common.navigation.Config
import core.common.navigation.rootNavigation
import core.design_system.Icons

class HomeComponent(val componentContext: ComponentContext) :
    ComponentContext by componentContext {
    internal val model = HomeModelState()

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