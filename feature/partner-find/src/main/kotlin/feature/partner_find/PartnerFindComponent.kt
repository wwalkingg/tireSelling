package feature.partner_find

import ModelState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import core.model.Partner
import core.model.PartnerSimple
import core.model.UserInfo
import core.network.utils.error
import core.network.utils.success
import httpClient
import io.ktor.client.request.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PartnerFindComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = instanceKeeper.getOrCreate { PartnerFindModelState() }
}

internal class PartnerFindModelState : ModelState() {
    private val _loadMyPartnerUIStateFlow = MutableStateFlow<LoadMyPartnerUIState>(LoadMyPartnerUIState.Loading)
    val loadMyPartnerUIState = _loadMyPartnerUIStateFlow.asStateFlow()

    private val _loadRecommendPartnerUIStateFlow = MutableStateFlow<LoadRecommendPartnerUIState>(LoadRecommendPartnerUIState.Loading)
    val loadRecommendPartnerUIStateFlow = _loadRecommendPartnerUIStateFlow.asStateFlow()

    init {
        loadMyPartner()
        loadRecommendPartner()
    }

    fun loadMyPartner() {
        coroutineScope.launch {
            _loadMyPartnerUIStateFlow.emit(LoadMyPartnerUIState.Loading)
            httpClient.get("/filter/myPartners").success<List<PartnerSimple>> {
                _loadMyPartnerUIStateFlow.emit(LoadMyPartnerUIState.Success(it))
            }.error {
                _loadMyPartnerUIStateFlow.emit(LoadMyPartnerUIState.Error)
            }
        }
    }

    fun loadRecommendPartner() {
        coroutineScope.launch {
            _loadRecommendPartnerUIStateFlow.emit(LoadRecommendPartnerUIState.Loading)
            httpClient.get("/recommendPartner").success<List<Partner>> {
                _loadRecommendPartnerUIStateFlow.emit(LoadRecommendPartnerUIState.Success(it))
            }.error {
                _loadRecommendPartnerUIStateFlow.emit(LoadRecommendPartnerUIState.Error)
            }
        }
    }
}

internal sealed interface LoadMyPartnerUIState {
    object Loading : LoadMyPartnerUIState
    data class Success(val data: List<PartnerSimple>) : LoadMyPartnerUIState
    object Error : LoadMyPartnerUIState
}

internal sealed interface LoadRecommendPartnerUIState {
    object Loading : LoadRecommendPartnerUIState
    data class Success(val data: List<Partner>) : LoadRecommendPartnerUIState
    object Error : LoadRecommendPartnerUIState
}
