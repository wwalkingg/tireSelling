package feature.course_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
        topBar = { TopBar() },
        bottomBar = {
            BottomBar(
                isCollect = component.modelState.isCollect,
                onCollectClick = {
                    if (component.modelState.isCollect) {
                        component.modelState.cancelCollect()
                    } else component.modelState.collect()
                },
                isSubscribe = component.modelState.isSubscribe,
                onSubscribeClick = {
                    if (component.modelState.isSubscribe) {
                        component.modelState.cancelSubscribe()
                    } else component.modelState.subscribe()
                }
            )
        }
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
                    CourseDetailContent(
                        modifier = Modifier,
                        course = (courseLoadState as CourseLoadState.Success).userCourseResp.course
                    )
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

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    isCollect: Boolean,
    onCollectClick: () -> Unit,
    isSubscribe: Boolean,
    onSubscribeClick: () -> Unit
) {
    Row(
        modifier.fillMaxWidth().padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onCollectClick) {
            if (isCollect)
                Icon(painterResource(Icons.heartStraightFill), contentDescription = null, tint = Color.Red)
            else Icon(painterResource(Icons.heartStraight), contentDescription = null, tint = Color.Red)
        }

        Button(onClick = onSubscribeClick) {
            if (isSubscribe)
                Text("已订阅")
            else Text("订阅")
        }
    }
}