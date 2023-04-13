package feature.my_subscribe

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.push
import core.common.navigation.Config
import core.common.navigation.rootNavigation
import core.design_system.component.loading
import core.ui.component.CourseCard
import core.ui.status_page.ErrorPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySubscribeScreen(modifier: Modifier = Modifier, component: MySubscribeComponent) {
    val loadMyCollectUIState by component.modelState.loadMyPlanUIStateFlow.collectAsState()
    Scaffold(topBar = { TopBar() }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (loadMyCollectUIState) {
                LoadMySubscribeUIState.Error -> {
                    ErrorPage(onRefreshClick = { component.modelState.loadMySubscribe() })
                }

                LoadMySubscribeUIState.Loading -> {
                    Box(Modifier.fillMaxSize().loading())
                }

                is LoadMySubscribeUIState.Success -> {
                    (loadMyCollectUIState as LoadMySubscribeUIState.Success).data.forEach {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(5.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            CourseCard(
                                modifier = Modifier.fillMaxWidth(),
                                course = it,
                                onClick = { rootNavigation.push(Config.RootConfig.CourseDetail(it)) })
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    TopAppBar(title = {
        Text("我的计划")
    })
}