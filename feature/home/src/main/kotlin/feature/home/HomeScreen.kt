package feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.push
import core.common.navigation.Config
import core.common.navigation.rootNavigation
import feature.home.me.MeComponent
import feature.home.me.MeScreen
import feature.home.recommend.RecommendComponent
import feature.home.recommend.RecommendScreen
import feature.home.statistic.StatisticComponent
import feature.home.statistic.StatisticScreen
import feature.my_subscribe.MySubscribeComponent
import feature.my_subscribe.MySubscribeScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, component: HomeComponent) {
    val scope = rememberCoroutineScope()
    val menu = BottomMenu.values()[
        if (component.model.pagerState.currentPage > 1)
            component.model.pagerState.currentPage + 1
        else component.model.pagerState.currentPage
    ]
    Scaffold(
        modifier,
        bottomBar = {
            BottomBar(
                modifier = Modifier.fillMaxWidth(),
                menu
            ) {
                scope.launch {
                    when (it) {
                        BottomMenu.Recommend -> component.model.pagerState.animateScrollToPage(0)
                        BottomMenu.Plan -> component.model.pagerState.animateScrollToPage(1)
                        BottomMenu.Person -> rootNavigation.push(Config.RootConfig.PersonHealth)
                        BottomMenu.Statistics -> component.model.pagerState.animateScrollToPage(2)
                        BottomMenu.User -> component.model.pagerState.animateScrollToPage(3)
                    }
                }
            }
        }
    ) { padding ->
        HorizontalPager(
            4,
            state = component.model.pagerState,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (it) {
                0 -> RecommendScreen(component = RecommendComponent(component))
                1 -> MySubscribeScreen(component = MySubscribeComponent(component))
                2 -> StatisticScreen(component = StatisticComponent(component))
                3 -> MeScreen(component = MeComponent(component))
            }
        }
    }
}

@Composable
private fun BottomBar(
    modifier: Modifier = Modifier,
    selected: BottomMenu,
    onSelectedChange: (BottomMenu) -> Unit
) {
    BottomAppBar(modifier) {
        BottomMenu.values().forEach {
            val isSelected = selected == it
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    onSelectedChange(it)
                },
                icon = {
                    Icon(
                        if (isSelected) painterResource(it.icon) else painterResource(it.iconSelected),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = { Text(it.title) })
        }
    }
}