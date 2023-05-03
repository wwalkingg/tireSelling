package feature.all_articles

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Coupon
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.network.api.Apis
import core.network.api.getMyCoupons
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AllArticlesComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext {
    internal val modelState = AllArticlesModelState()
}

internal class AllArticlesModelState : ModelState() {
    var selectedCoupon: Coupon? by  mutableStateOf(null)

    private val _loadCouponsUIStateFlow = MutableStateFlow<LoadUIState<List<Coupon>>>(LoadUIState.Loading)
    val loadCouponsUIStateFlow = _loadCouponsUIStateFlow.asStateFlow()

    fun loadMyCoupons() {
        coroutineScope.launch {
            Apis.Coupons.getMyCoupons()
                .onStart { _loadCouponsUIStateFlow.value = LoadUIState.Loading }
                .catch {
                    it.printStackTrace()
                    _loadCouponsUIStateFlow.value = LoadUIState.Error(it)
                }
                .collect {
                    _loadCouponsUIStateFlow.value = LoadUIState.Success(it)
                }
        }
    }

    init {
        loadMyCoupons()
    }
}
