package feature.course_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.arkivanov.decompose.router.stack.pop
import core.common.navigation.rootNavigation
import core.design_system.Icons
import core.design_system.component.loading
import core.ui.status_page.ErrorPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(modifier: Modifier = Modifier, component: CourseDetailComponent) {
    val courseLoadState by component.modelState.courseLoadStateFlow.collectAsState()
    Scaffold(
        topBar = { TopBar() }
    ) {
        Column(modifier = modifier.padding(it)) {
            when (courseLoadState) {
                CourseLoadState.Error -> {
                    ErrorPage(modifier = Modifier.fillMaxSize(), errorMsg = "出错啦", onRefreshClick = {})
                }

                CourseLoadState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize().loading())
                }

                is CourseLoadState.Success -> {
                    CourseDetailContent(course = (courseLoadState as CourseLoadState.Success).course)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier) {
    TopAppBar(title = { Text("课程详细") }, navigationIcon = {
        IconButton(onClick = { rootNavigation.pop() }) {
            Icon(painterResource(Icons.caretLeft), contentDescription = null)
        }
    })
}