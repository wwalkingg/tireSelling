package feature.partner_find

import ModelState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
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

    init {
        loadMyPartner()
    }

    fun loadMyPartner() {
        coroutineScope.launch {
            _loadMyPartnerUIStateFlow.emit(LoadMyPartnerUIState.Loading)
            httpClient.get("/filter/myPartner").success<List<PartnerSimple>> {
                _loadMyPartnerUIStateFlow.emit(LoadMyPartnerUIState.Success(it))
            }.error {
                _loadMyPartnerUIStateFlow.emit(LoadMyPartnerUIState.Error)
            }
        }
    }
}

internal sealed interface LoadMyPartnerUIState {
    object Loading : LoadMyPartnerUIState
    data class Success(val data: List<PartnerSimple>) : LoadMyPartnerUIState
    object Error : LoadMyPartnerUIState
}