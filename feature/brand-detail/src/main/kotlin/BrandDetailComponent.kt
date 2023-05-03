import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.BrandDetail
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.network.api.Apis
import core.network.api.getBrandDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class BrandDetailComponent(componentContext: ComponentContext, val id: Int) : ComponentContext by componentContext {
    internal val modelState = BrandDetailModelState(id)
}

internal class BrandDetailModelState(val id: Int) : ModelState() {
    private val _loadBrandDetailUIStateFlow = MutableStateFlow<LoadUIState<BrandDetail>>(LoadUIState.Loading)
    val loadBrandDetailUIStateFlow = _loadBrandDetailUIStateFlow.asStateFlow()

    fun loadBrandDetail() {
        coroutineScope.launch {
            Apis.Brand.getBrandDetail(id)
                .onStart {
                    _loadBrandDetailUIStateFlow.emit(LoadUIState.Loading)
                }
                .catch {
                    it.printStackTrace()
                    _loadBrandDetailUIStateFlow.emit(LoadUIState.Error(it))
                }
                .collect {
                    _loadBrandDetailUIStateFlow.emit(LoadUIState.Success(it))
                }
        }
    }

    init {
        loadBrandDetail()
    }
}

