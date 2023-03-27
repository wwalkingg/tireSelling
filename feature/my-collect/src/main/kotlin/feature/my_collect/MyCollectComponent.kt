package feature.my_collect

import ModelState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import core.model.Course
import core.network.utils.error
import core.network.utils.success
import httpClient
import io.ktor.client.request.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyCollectComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = instanceKeeper.getOrCreate { MyCollectModelState() }
}

internal class MyCollectModelState : ModelState() {
    private val _loadMyPlanUIStateFlow = MutableStateFlow<LoadMyPlanUIState>(LoadMyPlanUIState.Loading)
    val loadMyPlanUIStateFlow = _loadMyPlanUIStateFlow.asStateFlow()

    init {
        loadMyPlan()
    }

    fun loadMyPlan() {
        coroutineScope.launch {
            _loadMyPlanUIStateFlow.emit(LoadMyPlanUIState.Loading)
            httpClient.get("/filter/myPlan").success<List<Course>> {
                _loadMyPlanUIStateFlow.emit(LoadMyPlanUIState.Success(it))
            }.error {
                _loadMyPlanUIStateFlow.emit(LoadMyPlanUIState.Error)
            }
        }
    }

}

internal sealed interface LoadMyPlanUIState {
    object Loading : LoadMyPlanUIState
    data class Success(val data: List<Course>) : LoadMyPlanUIState
    object Error : LoadMyPlanUIState
}