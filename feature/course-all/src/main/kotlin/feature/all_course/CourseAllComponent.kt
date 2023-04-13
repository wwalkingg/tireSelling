package feature.all_course

import ModelState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.push
import core.common.navigation.Config
import core.common.navigation.rootNavigation
import core.model.Course
import core.model.CourseSortType
import core.network.utils.error
import core.network.utils.success
import httpClient
import io.ktor.client.request.get
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseAllComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext {
    fun navigationTo(courseDetail: Config.RootConfig.CourseDetail) {
        rootNavigation.push(courseDetail)
    }

    internal val modelState = CourseAllModelState()
}

internal class CourseAllModelState : ModelState() {
    var orderMethods by mutableStateOf(OrderMethods.UploadTime)
        private set
    private var _selectedCourseTypeId by mutableStateOf(0) // 0 代表全部
    var selectedCourseTypeId
        get() = _selectedCourseTypeId
        set(value) {
            _selectedCourseTypeId = value
            loadCourseAll()
        }

    private val _loadAllCourseFlow =
        MutableStateFlow<LoadAllCourseUIState>(LoadAllCourseUIState.Loading)
    val loadAllCourseFlow = _loadAllCourseFlow.asStateFlow()

    private val _loadCourseTypeFlow =
        MutableStateFlow<LoadCourseTypeUIState>(LoadCourseTypeUIState.Loading)
    val loadCourseTypeFlow = _loadCourseTypeFlow.asStateFlow()

    init {
        loadCourseType()
        loadCourseAll()
    }


    fun onOrderMethodClick(orderMethod: OrderMethods) {
        if (orderMethod != orderMethods) {
            loadCourseAll()
        }
        orderMethods = orderMethod
    }

    fun loadCourseAll() {
        coroutineScope.launch {
            _loadAllCourseFlow.emit(LoadAllCourseUIState.Loading)
            val isLoadAllCourse = selectedCourseTypeId == 0
            val url =
                if (isLoadAllCourse) "/course/getAllCourses" else "/getAllCoursesByType?type=${selectedCourseTypeId}"
            httpClient.get(url).success<List<Course>> {
                val orderedCourseList =
                    if (orderMethods == OrderMethods.Difficulty) it.sortedBy { it.difficulty } else it
                _loadAllCourseFlow.emit(LoadAllCourseUIState.Success(orderedCourseList))
            }.error {
                _loadAllCourseFlow.emit(LoadAllCourseUIState.Error)
            }
        }
    }

    fun loadCourseType() {
        coroutineScope.launch {
            _loadCourseTypeFlow.emit(LoadCourseTypeUIState.Loading)
            httpClient.get("/getAllCourseTypes").success<List<CourseSortType>> {
                _loadCourseTypeFlow.emit(
                    LoadCourseTypeUIState.Success(
                        listOf(
                            CourseSortType(
                                id = 0,
                                typeName = "全部"
                            )
                        ) + it
                    )
                )
            }.error {
                _loadCourseTypeFlow.emit(LoadCourseTypeUIState.Error)
            }
        }
    }
}

internal sealed interface LoadAllCourseUIState {
    object Loading : LoadAllCourseUIState
    data class Success(val data: List<Course>) : LoadAllCourseUIState
    object Error : LoadAllCourseUIState
}

internal sealed interface LoadCourseTypeUIState {
    object Loading : LoadCourseTypeUIState
    data class Success(val data: List<CourseSortType>) : LoadCourseTypeUIState
    object Error : LoadCourseTypeUIState
}