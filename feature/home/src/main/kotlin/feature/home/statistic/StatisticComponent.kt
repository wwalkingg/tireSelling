package feature.home.statistic

import ModelState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import core.model.Statistics
import core.network.utils.error
import core.network.utils.success
import httpClient
import io.ktor.client.request.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StatisticComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = instanceKeeper.getOrCreate { StatisticModelState() }
}

internal class StatisticModelState : ModelState() {
    private val _loadStatisticUIStateFlow = MutableStateFlow<LoadStatisticUIState>(LoadStatisticUIState.Loading)
    val loadStatisticUIStateFlow = _loadStatisticUIStateFlow.asStateFlow()

    init {
        loadStatistic()
    }

    fun loadStatistic() {
        coroutineScope.launch {
            _loadStatisticUIStateFlow.emit(LoadStatisticUIState.Loading)
            httpClient.get("/filter/statistics").success<Statistics> {
                _loadStatisticUIStateFlow.emit(LoadStatisticUIState.Success(it))
            }.error {
                _loadStatisticUIStateFlow.emit(LoadStatisticUIState.Error)
            }
        }
    }
}

internal sealed interface LoadStatisticUIState {
    object Loading : LoadStatisticUIState
    object Error : LoadStatisticUIState
    data class Success(val statistic: Statistics) : LoadStatisticUIState
}