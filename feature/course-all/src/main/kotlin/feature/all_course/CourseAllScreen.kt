package feature.all_course

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.common.navigation.Config
import core.design_system.component.loading
import core.model.CourseSortType
import core.ui.component.CourseCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseAllScreen(modifier: Modifier = Modifier, component: CourseAllComponent) {
    val loadCourseTypeState by component.modelState.loadCourseTypeFlow.collectAsState()
    val loadAllCourseState by component.modelState.loadAllCourseFlow.collectAsState()
    Scaffold(modifier = modifier, topBar = {
        Column {
            TopBar(orderMethods = component.modelState.orderMethods, onOrderMethodClick = {
                component.modelState.onOrderMethodClick(it)
            })
            when (loadCourseTypeState) {
                LoadCourseTypeUIState.Error -> {
                    Text(text = "加载课程类型失败")
                }

                LoadCourseTypeUIState.Loading -> {
                    Box(Modifier.fillMaxWidth().height(80.dp).loading())
                }

                is LoadCourseTypeUIState.Success -> {
                    TypeSelectBar(modifier = Modifier.fillMaxWidth(),
                        candidates = (loadCourseTypeState as LoadCourseTypeUIState.Success).data,
                        selectedId = component.modelState.selectedCourseTypeId,
                        onTypeSelect = {
                            component.modelState.selectedCourseTypeId = it
                        })
                }
            }
        }

    }) { padding ->
        Column(Modifier.padding(padding).verticalScroll(rememberScrollState())) {
            when (loadAllCourseState) {
                LoadAllCourseUIState.Error -> {
                    Text(text = "加载课程失败")
                }

                LoadAllCourseUIState.Loading -> {
                    Box(Modifier.fillMaxSize().loading())
                }

                is LoadAllCourseUIState.Success -> {
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        (loadAllCourseState as LoadAllCourseUIState.Success).data.forEach { course ->
                            CourseCard(modifier = Modifier.padding(vertical = 10.dp), course = course, onClick = {
                                component.navigationTo(Config.RootConfig.CourseDetail(it))
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TypeSelectBar(
    modifier: Modifier = Modifier, candidates: List<CourseSortType>, selectedId: Int, onTypeSelect: (Int) -> Unit
) {
    ScrollableTabRow(modifier = modifier, selectedTabIndex = candidates.indexOfFirst { it.id == selectedId }) {
        candidates.forEach {
            Tab(selected = it.id == selectedId, onClick = { onTypeSelect(it.id) }) {
                Text(text = it.typeName, modifier = Modifier.padding(vertical = 5.dp))
            }
        }
    }
}