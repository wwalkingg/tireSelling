package feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import feature.home.me.MeComponent
import feature.home.me.MeScreen
import feature.home.plan.PlanComponent
import feature.home.plan.PlanScreen
import feature.home.recommend.RecommendComponent
import feature.home.recommend.RecommendScreen
import feature.home.statistic.StatisticComponent
import feature.home.statistic.StatisticScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, component: HomeComponent) {
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier,
        bottomBar = {
            BottomBar(
                modifier = Modifier.fillMaxWidth(),
                BottomMenu.values()[component.model.pagerState.currentPage]
            ) {
                scope.launch {
                    component.model.pagerState.animateScrollToPage(it.ordinal)
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
                1 -> PlanScreen(component = PlanComponent(component))
                2 -> StatisticScreen(component = StatisticComponent(component))
                3 -> MeScreen(component = MeComponent(component))
            }
        }
    }
}

@Composable
private fun BottomBar(
    modifier: Modifier = Modifier,
    selected: BottomMenu, onSelectedChange: (BottomMenu) -> Unit
) {
    BottomAppBar(modifier) {
        BottomMenu.values().forEach {
            val isSelected = selected == it
            NavigationBarItem(
                selected = isSelected,
                onClick = { onSelectedChange(it) },
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