package feature.home.statistic

import ModelState
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StatisticComponent(componentContext: ComponentContext) : ComponentContext by componentContext {

}

class StatisticModelState : ModelState() {
    private val loadStatisticUIStateFlow = MutableStateFlow<LoadStatisticUIState>(LoadStatisticUIState.Loading)

    fun loadStatistic(){
        coroutineScope.launch {

        }

    }
}

internal sealed interface LoadStatisticUIState {
    object Loading : LoadStatisticUIState
    object Error : LoadStatisticUIState
    data class Success(val statistic: String) : LoadStatisticUIState
}