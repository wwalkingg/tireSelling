package feature.product_detail

import SmallLoadUIStateScaffold
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.android.core.model.Coupon
import components.CouponWithDetailButtonSheet
import core.component_base.LoadUIState
import core.network.api.Apis
import core.network.api.getMyCoupons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

@Composable
fun CouponSelectDialog(onDismissRequest: () -> Unit, onCouponSelect: (Coupon) -> Unit) {
    var couponsUIState: LoadUIState<List<Coupon>> by remember {
        mutableStateOf(LoadUIState.Loading)
    }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            Apis.Coupons.getMyCoupons()
                .onStart { couponsUIState = LoadUIState.Loading }
                .catch {
                    it.printStackTrace()
                    couponsUIState = LoadUIState.Error(it)
                }
                .collect {
                    couponsUIState = LoadUIState.Success(it)
                }
        }
    }
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .padding(vertical = 40.dp)
                .clip(MaterialTheme.shapes.medium)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "选择优惠券", style = MaterialTheme.typography.displaySmall)
            SmallLoadUIStateScaffold(loadUIState = couponsUIState) { coupons ->
                Column(modifier = Modifier
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    coupons.forEach { coupon ->
                        Box(modifier = Modifier
                            .height(100.dp)
                            .clickable { }) {
                            CouponWithDetailButtonSheet(modifier = Modifier, coupon = coupon, null)
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .clickable { onCouponSelect(coupon) })
                        }
                    }
                }
            }
        }
    }
}