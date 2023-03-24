package feature.course_all

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.push
import core.common.navigation.Config
import core.common.navigation.rootNavigation
import core.design_system.Icons
import core.design_system.component.loading
import core.ui.component.CoachSquareCard
import core.ui.status_page.ErrorPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoachAllScreen(modifier: Modifier = Modifier, component: CoachAllComponent) {
    val loadAllCourseUIState by component.modelState.loadAllCoachUIState.collectAsState()
    Scaffold(modifier, topBar = { TopBar() }) { paddingValues
        ->
        when (loadAllCourseUIState) {
            LoadAllCoachUIState.Error -> {
                ErrorPage { component.modelState.loadAllCoach() }
            }

            LoadAllCoachUIState.Loading -> {
                Box(Modifier.fillMaxSize().loading())
            }

            is LoadAllCoachUIState.Success -> {
                val list = (loadAllCourseUIState as LoadAllCoachUIState.Success).data
                LazyHorizontalGrid(
                    modifier = Modifier.padding(paddingValues).fillMaxSize(),
                    rows = GridCells.Fixed(2)
                ) {
                    items(list.size) {
                        CoachSquareCard(
                            modifier = Modifier.padding(10.dp).background(Color.Red),
                            coach = list[it],
                            onClick = { rootNavigation.push(Config.RootConfig.CoachDetail(it)) })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    TopAppBar(
        title = { Text("教练") },
        navigationIcon = { Icon(painterResource(Icons.caretLeft), contentDescription = null) })
}
