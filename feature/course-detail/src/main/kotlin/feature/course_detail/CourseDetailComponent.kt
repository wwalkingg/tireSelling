package feature.course_detail

import ModelState
import com.arkivanov.decompose.ComponentContext
import core.model.Course
import core.network.utils.error
import core.network.utils.success
import httpClient
import io.ktor.client.request.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseDetailComponent(componentContext: ComponentContext, val id: Int) : ComponentContext by componentContext {
    internal val modelState = CourseDetailModelState(id)
}

class CourseDetailModelState(val id: Int) : ModelState() {
    private val _courseLoadStateFlow = MutableStateFlow<CourseLoadState>(CourseLoadState.Loading)
    val courseLoadStateFlow = _courseLoadStateFlow.asStateFlow()

    init {
        loadCourse()
    }

    fun loadCourse() {
        coroutineScope.launch {
            _courseLoadStateFlow.emit(CourseLoadState.Loading)
            httpClient.get("/filter/getCoursesById?courseId=$id")
                .success<Course> {
                    _courseLoadStateFlow.emit(CourseLoadState.Success(it))
                }
                .error {
                    _courseLoadStateFlow.emit(CourseLoadState.Error)
                }
        }
    }
}

sealed interface CourseLoadState {
    object Loading : CourseLoadState
    object Error : CourseLoadState
    data class Success(val course: Course) : CourseLoadState
}
