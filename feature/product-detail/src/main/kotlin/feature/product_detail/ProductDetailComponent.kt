package feature.product_detail

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.*
import com.example.android.core.model.param.OrderParam
import core.component_base.LoadUIState
import core.component_base.ModelState
import core.component_base.PostUIState
import core.datastore.AddressStore
import core.datastore.ShopCar
import core.network.api.Apis
import core.network.api.createNewOrder
import core.network.api.getProductDetail
import core.network.api.getRecommendProducts
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@Stable
class ProductDetailComponent(componentContext: ComponentContext, id: Int) :
    ComponentContext by componentContext {
    internal val modelState = ProductDetailModelState(id)
}

internal class ProductDetailModelState(private val id: Int) : ModelState() {

    private val _loadProductDetailUIStateFlow: MutableStateFlow<LoadUIState<ProductsDetail>> =
        MutableStateFlow(LoadUIState.Loading)
    val loadProductDetailUIStateFlow = _loadProductDetailUIStateFlow.asStateFlow()

    private val _createOrderUIStateFlow: MutableStateFlow<PostUIState> =
        MutableStateFlow(PostUIState.None)
    val createOrderUIStateFlow = _createOrderUIStateFlow.asStateFlow()

    private val _loadRecommendProductUIStateFlow: MutableStateFlow<LoadUIState<List<Product>>> =
        MutableStateFlow(LoadUIState.Loading)
    val loadRecommendProductUIStateFlow = _loadRecommendProductUIStateFlow.asStateFlow()

    var storeId: Int? = 0

    fun loadProductDetail() {
        coroutineScope.launch {
            Apis.Product.getProductDetail(id)
                .onStart { _loadProductDetailUIStateFlow.emit(LoadUIState.Loading) }
                .catch {
                    _loadProductDetailUIStateFlow.emit(LoadUIState.Error(it))
                    it.printStackTrace()
                }
                .collect {
                    storeId = it.store?.id
                    _loadProductDetailUIStateFlow.emit(LoadUIState.Success(it))
                }
        }
    }


    fun buy(data: List<Pair<Product, Int>>, address: Address, coupon: Coupon?) {
        coroutineScope.launch {
            val param = OrderParam(
                coupons = if (coupon == null) emptyList() else listOf(coupon),
                note = "",
                products = data.map { ProductAndCount(it.first, it.second) },
                receiverAddress = address.address + address.detailAddress,
                receiverName = address.name,
                receiverPhone = address.phone
            )
            Apis.Order.createNewOrder(param)
                .onStart { _createOrderUIStateFlow.emit(PostUIState.Loading) }
                .catch {
                    _createOrderUIStateFlow.emit(PostUIState.Error(it))
                    it.printStackTrace()
                    delay(2000L)
                    _createOrderUIStateFlow.emit(PostUIState.None)

                }
                .collect {
                    _createOrderUIStateFlow.emit(PostUIState.Success("预约成功"))
                    delay(2000L)
                    _createOrderUIStateFlow.emit(PostUIState.None)
                }
        }
    }

    fun loadAddressList(): List<Address> {
        return AddressStore.retrieve().addresses
    }

    fun addToShopCar(product: Product) {
        ShopCar.retrieve().apply {
            val list = this.productList
            val index = list.indexOfFirst { it.first == product }
            if (index == -1) {
                this.copy(productList = (list + (product to 1)).toMutableList()).store()
            } else {
                val newProduct = list[index].first
                val newCount = list[index].second + 1
                this.copy(productList = (list - list[index] + (newProduct to newCount)).toMutableList()).store()
            }

        }
    }

    fun loadRecommendProduct() {
        coroutineScope.launch {
            Apis.Product.getRecommendProducts()
                .onStart { _loadRecommendProductUIStateFlow.emit(LoadUIState.Loading) }
                .catch {
                    it.printStackTrace()
                    _loadRecommendProductUIStateFlow.emit(LoadUIState.Error(it))
                }
                .collect {
                    _loadRecommendProductUIStateFlow.emit(LoadUIState.Success(it))
                }
        }
    }

    init {
        loadProductDetail()
        loadRecommendProduct()
    }

}
