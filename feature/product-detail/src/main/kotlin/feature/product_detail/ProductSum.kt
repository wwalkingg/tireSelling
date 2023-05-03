package feature.product_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arkivanov.decompose.router.stack.push
import com.example.android.core.model.Address
import com.example.android.core.model.Coupon
import com.example.android.core.model.Product
import core.common.Config
import core.common.NavConfig
import core.common.navigation
import core.datastore.AddressStore
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductSum(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    onBuy: (List<Pair<Product, Int>>, Address) -> Unit,
    productAndNumbers: PersistentList<Pair<Product, Int>>
) {
    val addressList = remember { AddressStore.retrieve().addresses }
    var selectedAddress by remember { mutableStateOf(addressList.firstOrNull()) }
    if (isVisible) {
        ModalBottomSheet(onDismissRequest = onDismissRequest) {
            Text(
                "确认订单",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Spacer(Modifier.height(10.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                productAndNumbers.forEach { productAndNumber ->
                    val product = productAndNumber.first
                    val number = productAndNumber.second
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.extraLarge
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Max),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = Config.baseImgUrl + product.image,
                                contentDescription = null,
                                modifier = Modifier
                                    .weight(3f)
                                    .fillMaxHeight(),
                                contentScale = ContentScale.FillHeight
                            )
                            Spacer(Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(9f)) {
                                Text(product.name, style = MaterialTheme.typography.titleLarge)
                                Text(
                                    String.format("CNY %.2f", product.price * number),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                            Text(
                                text = "× $number",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            )
                        }
                    }
                }
            }
            var selectedCoupon: Coupon? by remember {
                mutableStateOf(null)
            }

            val sum0 = productAndNumbers.sumOf {
                it.first.price * it.second
            }
            val sum = if (selectedCoupon == null) sum0
            else {
                if (selectedCoupon!!.type == 1) {
                    sum0 - selectedCoupon!!.cashback
                } else sum0 * selectedCoupon!!.discount / 100
            }

            Text(
                text = "合计: CNY ${String.format("%.2f", sum)}",
                style = MaterialTheme.typography.titleMedium,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(10.dp),
            )
            Text(
                "配送地址",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            var isAddressSelectDialogVisible by remember {
                mutableStateOf(false)
            }
            if (addressList.isEmpty()) {
                AssistChip(
                    onClick = { navigation.push(NavConfig.AddressManagement) },
                    label = { Text("还未添加过地址,点击添加") },
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            } else {
                AssistChip(
                    onClick = { isAddressSelectDialogVisible = true },
                    label = { Text(selectedAddress?.address ?: "空地址") },
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
            Text(
                "选择优惠券",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            var isCouponSelectVisbible by remember {
                mutableStateOf(false)
            }
            val couponText = if (selectedCoupon == null) "未选择优惠券"
            else {
                if (selectedCoupon!!.type == 1) "${selectedCoupon!!.cashback}减免券" else "${selectedCoupon!!.discount}折扣券"
            }
            AssistChip(
                onClick = { isCouponSelectVisbible = true },
                label = { Text(couponText) },
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            if (isCouponSelectVisbible) {
                CouponSelectDialog(onDismissRequest = { isCouponSelectVisbible = false }) {
                    selectedCoupon = it
                    isCouponSelectVisbible = false
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    if (selectedAddress != null) {
                        onBuy(productAndNumbers, selectedAddress!!)
                    }

                }, modifier = Modifier.fillMaxWidth(.8f)) {
                    Text("结算")
                }
            }
            if (isAddressSelectDialogVisible) {
                AddressSelectDialog(
                    onDismissRequest = { isAddressSelectDialogVisible = false },
                    addressList = addressList.toPersistentList(),
                    selectedAddress = selectedAddress,
                    onSelect = { selectedAddress = it },
                )
            }
        }
    }

}