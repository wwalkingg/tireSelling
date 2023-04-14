package feature.my_collect

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.push
import core.common.navigation.Config
import core.common.navigation.rootNavigation
import core.design_system.component.loading
import core.ui.component.CourseCard
import core.ui.component.NavigationTopBar
import core.ui.status_page.ErrorPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCollectScreen(modifier: Modifier = Modifier, component: MyCollectComponent) {
    val loadMyCollectUIState by component.modelState.loadMyPlanUIStateFlow.collectAsState()
    Scaffold(topBar = { NavigationTopBar(title = "我的收藏") }) { padding ->
        Column(modifier = Modifier.padding(padding).verticalScroll(rememberScrollState())) {
            when (loadMyCollectUIState) {
                LoadMyPlanUIState.Error -> {
                    ErrorPage(onRefreshClick = { component.modelState.loadMyPlan() })
                }

                LoadMyPlanUIState.Loading -> {
                    Box(Modifier.fillMaxSize().padding(top = 50.dp)) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }

                is LoadMyPlanUIState.Success -> {
                    (loadMyCollectUIState as LoadMyPlanUIState.Success).data.forEach {
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