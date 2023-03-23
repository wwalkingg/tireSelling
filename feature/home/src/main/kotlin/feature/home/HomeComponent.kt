package feature.home

import HomeModelState
import com.arkivanov.decompose.ComponentContext
import core.design_system.Icons

class HomeComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val model = HomeModelState()

    internal fun navigationTo(){

    }
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