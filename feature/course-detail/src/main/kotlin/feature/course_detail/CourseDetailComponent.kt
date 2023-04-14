package feature.course_detail

import ModelState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import core.design_system.component.rootSnackBarHostState
import core.model.UserCourseResp
import core.network.utils.error
import core.network.utils.success
import httpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseDetailComponent(componentContext: ComponentContext, val id: Int) : ComponentContext by componentContext {
    internal val modelState = CourseDetailModelState(id)
}

class CourseDetailModelState(val id: Int) : ModelState() {
    var isCollect by mutableStateOf(false)
    var isSubscribe by mutableStateOf(false)

    private val _courseLoadStateFlow = MutableStateFlow<CourseLoadState>(CourseLoadState.Loading)
    val courseLoadStateFlow = _courseLoadStateFlow.asStateFlow()

    var courseId: Int? = null

    init {
        loadCourse()
    }

    fun loadCourse() {
        coroutineScope.launch {
            _courseLoadStateFlow.emit(CourseLoadState.Loading)
            httpClient.get("/filter/getCoursesById?courseId=$id")
                .success<UserCourseResp> {
                    courseId = it.course.id
                    isCollect = it.isPlaned
                    isSubscribe = it.isSubscribed
                    _courseLoadStateFlow.emit(CourseLoadState.Success(it))
                }
                .error {
                    _courseLoadStateFlow.emit(CourseLoadState.Error)
                }
        }
    }

    fun collect() {
        if (courseId == null) {
            coroutineScope.launch {
                rootSnackBarHostState.showSnackbar("请等待加载", withDismissAction = true)
            }
            return
        }
        isCollect = true
        coroutineScope.launch {
            httpClient.post("/filter/joinPlan") { parameter("courseId", courseId) }.success {
                rootSnackBarHostState.showSnackbar("收藏成功", withDismissAction = true)
            }.error { rootSnackBarHostState.showSnackbar("收藏失败", withDismissAction = true) }
        }
    }

    fun cancelCollect() {
        isCollect = false
        coroutineScope.launch {
            httpClient.post("/filter/cancelPlanCourse") { parameter("courseId", courseId) }.success {
                rootSnackBarHostState.showSnackbar("取消收藏成功", withDismissAction = true)
            }.error { rootSnackBarHostState.showSnackbar("取消收藏失败", withDismissAction = true) }
        }
    }

    fun subscribe() {
        isSubscribe = !isSubscribe
        coroutineScope.launch {
            coroutineScope.launch {
                httpClient.post("/filter/joinSubscribe") {
                    parameter("courseId", courseId)
                }.success {
                    rootSnackBarHostState.showSnackbar("订阅成功", withDismissAction = true)
                }.error { rootSnackBarHostState.showSnackbar("订阅失败", withDismissAction = true) }
            }
        }
    }

    fun cancelSubscribe() {
        isSubscribe = false
        coroutineScope.launch {
            coroutineScope.launch {
                httpClient.post("/filter/cancelSubscribe") { parameter("courseId", courseId) }.success {
                    rootSnackBarHostState.showSnackbar("取消订阅成功", withDismissAction = true)
                }.error { rootSnackBarHostState.showSnackbar("取消订阅失败", withDismissAction = true) }
            }
        }
    }
}

sealed interface CourseLoadState {
    object Loading : CourseLoadState
    object Error : CourseLoadState
    data class Success(val userCourseResp: UserCourseResp) : CourseLoadState
}
