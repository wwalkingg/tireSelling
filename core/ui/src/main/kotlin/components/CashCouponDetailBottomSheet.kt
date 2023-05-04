package components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android.core.model.Coupon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashCouponDetailBottomSheet(isVisible: Boolean, onDismissRequest: () -> Unit, coupon: Coupon) {
    if (isVisible) {
        ModalBottomSheet(onDismissRequest = onDismissRequest) {
            CashCouponDetail(coupon)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashCouponDetailAndReceiveBottomSheet(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    coupon: Coupon,
    onReceiveClick: (() -> Unit)?
) {
    if (isVisible) {
        ModalBottomSheet(onDismissRequest = onDismissRequest) {
            CashCouponDetail(coupon, onReceiveClick)
        }
    }
}

@Composable
fun CashCouponDetail(coupon: Coupon, onReceiveClick: (() -> Unit)? = null) {
    val containerColor = if (coupon.type == 1) Color(0xFFdea853) else Color(0xFFaa80ec)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)

    ) {
        CompositionLocalProvider(LocalContentColor provides containerColor) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp), horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = if (coupon.type == 1) "现金券" else "折扣券",
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.alignByBaseline()
                    )
                    if (coupon.type == 1){
                        Text(
                            text = coupon.cashback.toString(),
                            style = MaterialTheme.typography.displaySmall,
                            fontFamily = FontFamily.Cursive,
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.End,
                            lineHeight = 20.sp,
                            modifier = Modifier.alignByBaseline()
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "元",
                            style = MaterialTheme.typography.titleMedium,
                            lineHeight = 20.sp,
                            modifier = Modifier.alignByBaseline()
                        )
                    }else{
                        Text(
                            text = coupon.discount.toString(),
                            style = MaterialTheme.typography.displaySmall,
                            fontFamily = FontFamily.Cursive,
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.End,
                            lineHeight = 20.sp,
                            modifier = Modifier.alignByBaseline()
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "折",
                            style = MaterialTheme.typography.titleMedium,
                            lineHeight = 20.sp,
                            modifier = Modifier.alignByBaseline()
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = coupon.name,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Text(
                    text = "使用条件",
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    text = "满${coupon.miniAmount}元可用",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = coupon.usageInstructions,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = "${coupon.startDate}至${coupon.expiryDate}",
                    style = MaterialTheme.typography.bodyLarge,
                )

                Row(horizontalArrangement = Arrangement.End) {
                    Button(onClick = {}) {
                        Text(text = "确定")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    if (onReceiveClick != null) {
                        Button(onClick = onReceiveClick) {
                            Text(text = "领取")
                        }
                    }
                }
            }
        }
    }
}

@Preview(device = "spec:width=900dp,height=3340dp,dpi=440")
@Composable
fun CashCouponDetailPreview() {
    val coupon = Coupon(
        cashback = 0.0,
        discount0 = 0.85,
        expiryDate = "2023-05-31",
        id = 12345,
        miniAmount = 1000.0,
        name = "20% off on all items",
        startDate = "2023-05-01",
        storeId = 67890,
        type = 1,
        usageInstructions = "Enter coupon code at checkout to receive 20% off on all items."
    )
    CashCouponDetail(coupon)
}