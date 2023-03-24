package feature.my_subscribe

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

class MySubscribeComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = instanceKeeper.getOrCreate { MySubscribeModelState() }
}

internal class MySubscribeModelState : ModelState() {
    private val _loadMySubscribeUIStateFlow = MutableStateFlow<LoadMySubscribeUIState>(LoadMySubscribeUIState.Loading)
    val loadMyPlanUIStateFlow = _loadMySubscribeUIStateFlow.asStateFlow()

    init {
        loadMySubscribe()
    }

    fun loadMySubscribe() {
        coroutineScope.launch {
            _loadMySubscribeUIStateFlow.emit(LoadMySubscribeUIState.Loading)
            httpClient.get("/filter/mySubscribe").success<List<Course>> {
                _loadMySubscribeUIStateFlow.emit(LoadMySubscribeUIState.Success(it))
            }.error {
                _loadMySubscribeUIStateFlow.emit(LoadMySubscribeUIState.Error)
            }
        }
    }

}

internal sealed interface LoadMySubscribeUIState {
    object Loading : LoadMySubscribeUIState
    data class Success(val data: List<Course>) : LoadMySubscribeUIState
    object Error : LoadMySubscribeUIState
}