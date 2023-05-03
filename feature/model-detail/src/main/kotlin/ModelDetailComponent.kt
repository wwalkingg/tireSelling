import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.ModelDetail
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.network.api.Apis
import core.network.api.getModelDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ModelDetailComponent(componentContext: ComponentContext,val id:Int) : ComponentContext by componentContext {
    internal val modelState = ModelDetailModelState(id)
}

internal class ModelDetailModelState(val id:Int) : ModelState() {
    private val _loadModelDetailUIStateFlow = MutableStateFlow<LoadUIState<ModelDetail>>(LoadUIState.Loading)
    val loadModelDetailUIStateFlow = _loadModelDetailUIStateFlow.asStateFlow()

    fun loadModelDetail() {
        coroutineScope.launch {
            Apis.Model.getModelDetail(id)
                .onStart { _loadModelDetailUIStateFlow.emit(LoadUIState.Loading) }
                .catch {
                    it.printStackTrace()
                    _loadModelDetailUIStateFlow.emit(LoadUIState.Error(it))
                }
                .collect {
                    _loadModelDetailUIStateFlow.emit(LoadUIState.Success(it))
                }
        }
    }

    init {
        loadModelDetail()
    }
}


