package feature.partner_find

import ModelState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import core.model.Partner
import core.model.PartnerSimple
import core.model.UserInfo
import core.network.utils.error
import core.network.utils.success
import httpClient
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PartnerFindComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = instanceKeeper.getOrCreate { PartnerFindModelState() }
}

internal class PartnerFindModelState : ModelState() {
    var isLoading by mutableStateOf(false)
    val snackBarState = SnackbarHostState()
    private val _loadMyPartnerUIStateFlow = MutableStateFlow<LoadMyPartnerUIState>(LoadMyPartnerUIState.Loading)
    val loadMyPartnerUIState = _loadMyPartnerUIStateFlow.asStateFlow()

    private val _loadRecommendPartnerUIStateFlow =
        MutableStateFlow<LoadRecommendPartnerUIState>(LoadRecommendPartnerUIState.Loading)
    val loadRecommendPartnerUIStateFlow = _loadRecommendPartnerUIStateFlow.asStateFlow()

    init {
        loadMyPartner()
        loadRecommendPartner()
    }

    var myPartner = mutableListOf<PartnerSimple>()
    fun loadMyPartner() {
        coroutineScope.launch {
            _loadMyPartnerUIStateFlow.emit(LoadMyPartnerUIState.Loading)
            httpClient.get("/filter/myPartners").success<List<PartnerSimple>> {
                myPartner = it.toMutableList()
                _loadMyPartnerUIStateFlow.emit(LoadMyPartnerUIState.Success(it))
            }.error {
                _loadMyPartnerUIStateFlow.emit(LoadMyPartnerUIState.Error)
            }
        }
    }

    var recommendList: MutableList<Partner> = mutableListOf()

    fun loadRecommendPartner() {
        coroutineScope.launch {
            _loadRecommendPartnerUIStateFlow.emit(LoadRecommendPartnerUIState.Loading)
            httpClient.get("/recommendPartner").success<List<Partner>> {
                recommendList = it.toMutableList()
                _loadRecommendPartnerUIStateFlow.emit(LoadRecommendPartnerUIState.Success(it))
            }.error {
                _loadRecommendPartnerUIStateFlow.emit(LoadRecommendPartnerUIState.Error)
            }
        }
    }

    fun bePartner(partner: Partner) {
        coroutineScope.launch {
            isLoading = true
            httpClient.post("/filter/bePartner") {
                contentType(ContentType.Application.Json)
                setBody(
                    mapOf(
                        "partnerId" to partner.id
                    )
                )
            }.success {
                isLoading = false
                loadMyPartner()
                loadRecommendPartner()
                snackBarState.showSnackbar(message = "添加成功", withDismissAction = true)
            }.error {
                isLoading = false
                snackBarState.showSnackbar(message = "添加失败", withDismissAction = true)
            }
        }
    }

    fun cancelPartner(partner: PartnerSimple) {
        coroutineScope.launch {
            isLoading = true
            httpClient.post("/filter/deletePartner") {
                parameter("partnerId", partner.id)
            }.success {
                isLoading = false
                loadMyPartner()
                loadRecommendPartner()
                snackBarState.showSnackbar(message = "取消成功", withDismissAction = true)
            }.error {
                isLoading = false
                snackBarState.showSnackbar(message = "取消失败", withDismissAction = true)
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
