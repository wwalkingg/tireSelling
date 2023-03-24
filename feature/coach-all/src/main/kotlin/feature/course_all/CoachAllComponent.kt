package feature.course_all

import ModelState
import com.arkivanov.decompose.ComponentContext
import core.model.Coach
import core.network.utils.error
import core.network.utils.success
import httpClient
import io.ktor.client.request.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CoachAllComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = CoachAllModelState()
}

internal class CoachAllModelState : ModelState() {
    private val _loadAllCoachUIState = MutableStateFlow<LoadAllCoachUIState>(LoadAllCoachUIState.Loading)
    val loadAllCoachUIState = _loadAllCoachUIState.asStateFlow()

    init {
        loadAllCoach()
    }

    fun loadAllCoach() {
        coroutineScope.launch {
            _loadAllCoachUIState.emit(LoadAllCoachUIState.Loading)
            httpClient.get("/listAllCoaches")
                .success<List<Coach>> {
                    _loadAllCoachUIState.emit(LoadAllCoachUIState.Success(it))
                }
                .error {
                    _loadAllCoachUIState.emit(LoadAllCoachUIState.Error)
                }
        }
    }
}

internal sealed interface LoadAllCoachUIState {
    object Loading : LoadAllCoachUIState
    object Error : LoadAllCoachUIState
    data class Success(val data: List<Coach>) : LoadAllCoachUIState
}