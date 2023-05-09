import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Coupon
import com.example.android.core.model.CouponPackage
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.component_base.PostUIState
import core.network.api.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CouponCenterComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext {
    internal val modelState = CouponCenterModelState()

}

internal class CouponCenterModelState : ModelState() {
    private val _loadAllCouponsUIStateFlow =
        MutableStateFlow<LoadUIState<List<Coupon>>>(LoadUIState.Loading)
    val loadAllCouponsUIStateFlow = _loadAllCouponsUIStateFlow.asStateFlow()

    private val _loadAllCouponPackageUIStateFlow =
        MutableStateFlow<LoadUIState<List<CouponPackage>>>(LoadUIState.Loading)
    val loadAllCouponPackageUIStateFlow = _loadAllCouponPackageUIStateFlow.asStateFlow()

    fun loadAllCoupons() {
        coroutineScope.launch {
            Apis.Coupons.getAllCoupons()
                .onStart { _loadAllCouponsUIStateFlow.value = LoadUIState.Loading }
                .catch {
                    it.printStackTrace()
                    _loadAllCouponsUIStateFlow.value = LoadUIState.Error(it)
                }
                .collect {
                    _loadAllCouponsUIStateFlow.value = LoadUIState.Success(it)
                }
        }
    }

    fun loadAllCouponPackages() {
        coroutineScope.launch {
            Apis.Coupons.getAllCouponPackage()
                .onStart { _loadAllCouponPackageUIStateFlow.value = LoadUIState.Loading }
                .catch {
                    it.printStackTrace()
                    _loadAllCouponPackageUIStateFlow.value = LoadUIState.Error(it)
                }
                .collect {
                    _loadAllCouponPackageUIStateFlow.value = LoadUIState.Success(it)
                }
        }
    }

    private val _receiveCouponUIStateFlow = MutableStateFlow<PostUIState>(PostUIState.None)
    val receiveCouponUIStateFlow = _receiveCouponUIStateFlow.asStateFlow()
    fun receiveCoupon(coupon: Coupon) {
        coroutineScope.launch {
            Apis.Coupons.receiveCoupon(coupon.id)
                .onStart { _receiveCouponUIStateFlow.emit(PostUIState.Loading) }
                .catch {
                    it.printStackTrace()
                    _receiveCouponUIStateFlow.emit(PostUIState.Error(it))
                    delay(2000L)
                    _receiveCouponUIStateFlow.emit(PostUIState.None)
                }
                .collect {
                    _receiveCouponUIStateFlow.emit(PostUIState.Success("领取成功"))
                    delay(2000L)
                    _receiveCouponUIStateFlow.emit(PostUIState.None)
                    loadAllCoupons()
                }
        }
    }

    fun receiveCouponPackage(couponPackage: CouponPackage) {
        coroutineScope.launch {
            Apis.Coupons.redeemCoupon(couponPackage.id)
                .onStart { _receiveCouponUIStateFlow.emit(PostUIState.Loading) }
                .catch {
                    it.printStackTrace()
                    _receiveCouponUIStateFlow.emit(PostUIState.Error(it))
                    delay(2000L)
                    _receiveCouponUIStateFlow.emit(PostUIState.None)
                }
                .collect {
                    _receiveCouponUIStateFlow.emit(PostUIState.Success("领取成功"))
                    delay(2000L)
                    _receiveCouponUIStateFlow.emit(PostUIState.None)
                    loadAllCouponPackages()
                }
        }
    }

    init {
        loadAllCouponPackages()
        loadAllCoupons()
    }
}
