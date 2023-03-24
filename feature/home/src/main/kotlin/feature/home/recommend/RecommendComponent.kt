package feature.home.recommend

import ModelState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.instancekeeper.getOrCreate
import core.common.navigation.Config
import core.common.navigation.rootNavigation
import core.model.Recommend
import core.network.utils.error
import core.network.utils.success
import httpClient
import io.ktor.client.request.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecommendComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext {

    internal val modelState = instanceKeeper.getOrCreate { RecommendModelState() }

    internal fun onCourseAllFunctionalMenuClick() {
        rootNavigation.push(Config.RootConfig.CourseAll)
    }


    internal fun onPersonHealthFunctionalMenuClick() {
        rootNavigation.push(Config.RootConfig.PersonHealth)
    }

    internal fun onCoachAllFunctionalMenuClick() {
        rootNavigation.push(Config.RootConfig.CoachAll)
    }

    internal fun onPartnerFindFunctionalMenuClick() {
        rootNavigation.push(Config.RootConfig.PartnerFind)
    }

}

internal class RecommendModelState : ModelState() {
    private val _loadRecommendUIStateFlow = MutableStateFlow<LoadRecommendUIState>(LoadRecommendUIState.Loading)
    val loadRecommendUIStateFlow = _loadRecommendUIStateFlow.asStateFlow()

    init {
        loadRecommend()
    }
    fun loadRecommend() {
        coroutineScope.launch {
            _loadRecommendUIStateFlow.emit(LoadRecommendUIState.Loading)
            httpClient.get("/recommend").success<Recommend> {
                _loadRecommendUIStateFlow.emit(LoadRecommendUIState.Success(it))
            }.error {
                _loadRecommendUIStateFlow.emit(LoadRecommendUIState.Error)
            }
        }
    }
}

internal sealed interface LoadRecommendUIState {
    object Loading : LoadRecommendUIState
    data class Success(val data: Recommend) : LoadRecommendUIState
    object Error : LoadRecommendUIState
}