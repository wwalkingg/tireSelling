package feature.all_course

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.design_system.component.loading
import core.model.CourseSortType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseAllScreen(modifier: Modifier = Modifier, component: CourseAllComponent) {
    val loadCourseTypeState by component.modelState.loadCourseTypeFlow.collectAsState()
    val loadAllCourseState by component.modelState.loadAllCourseFlow.collectAsState()
    Scaffold(modifier = modifier, topBar = {
        TopBar(orderMethods = component.modelState.orderMethods, onOrderMethodClick = {
            component.modelState.onOrderMethodClick(it)
        })
        val loadingModifier = remember {
            Modifier
                .loading()
                .heightIn(min = 20.dp)
        }
        TypeSelectBar(
            modifier = Modifier.fillMaxWidth().then(if(load)),
            candidates = listOf(),
            selectedId = 0,
            onTypeSelect = {
                component.modelState.selectedCourseTypeId = it
            })
    }) {
        Column(Modifier.padding(it)) {

        }
    }
}

@Composable
fun TypeSelectBar(
    modifier: Modifier = Modifier,
    candidates: List<CourseSortType>,
    selectedId: Int,
    onTypeSelect: (Int) -> Unit
) {
    ScrollableTabRow(modifier = modifier,
        selectedTabIndex = candidates.indexOfFirst { it.id == selectedId }) {
        candidates.forEach {
            Tab(selected = it.id == selectedId, onClick = { onTypeSelect(it.id) }) {
                Text(text = it.typeName)
            }
        }
    }
}