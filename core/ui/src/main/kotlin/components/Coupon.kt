package components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.example.android.core.model.Coupon


@Composable
fun Coupon(modifier: Modifier, coupon: Coupon, onClick: (() -> Unit)? = null) {
    if (coupon.type == 1) {
        CashCoupon(modifier, coupon = coupon, onClick)
    } else DiscountCoupon(modifier, coupon = coupon, onClick)
}


@Composable
fun CouponBackground(colors: List<Color>, icon: @Composable () -> Unit) {
    val brush = Brush.linearGradient(
        colors = colors,
        tileMode = androidx.compose.ui.graphics.TileMode.Clamp
    )
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
    ) {
        val min = min(maxHeight, maxWidth)
        val minPixel = with(LocalDensity.current) {
            min.toPx()
        }
        val iconModifier = Modifier
            .size(min)
            .graphicsLayer {
                transformOrigin = TransformOrigin.Center
                rotationZ = 135f
                translationX = -minPixel / 4
                translationY = -minPixel / 4
            }
        Box(modifier = iconModifier) {
            icon()
        }
    }
}


@Composable
fun CouponWithDetailButtonSheet(modifier: Modifier, coupon: Coupon, onReceiveClick: (() -> Unit)?) {
    Box {
        var isVisible by remember {
            mutableStateOf(false)
        }
        if (coupon.type == 1) {
            CashCoupon(modifier, coupon = coupon) { isVisible = true }
        } else DiscountCoupon(modifier, coupon = coupon) { isVisible = true }
        CashCouponDetailAndReceiveBottomSheet(
            isVisible = isVisible,
            onDismissRequest = { isVisible = false },
            coupon = coupon,
            onReceiveClick
        )
    }
}

@Composable
fun CashCoupon(modifier: Modifier = Modifier, coupon: Coupon, onClick: (() -> Unit)?) {
    Box(
        modifier = modifier
            .couponModifier()
            .let { if (onClick != null) it.clickable { onClick() } else it }
    ) {
        CouponBackground(
            colors = listOf(
                Color(0xFFe7cf68),
                Color(0xFFdea853)
            ),
        ) {
            Icon(
                imageVector = Icons.Default.Money,
                contentDescription = null,
                tint = Color(0xFFBE9E49),
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp), horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "现金券",
                color = Color.White,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.Black.copy(.3f))
                    .padding(horizontal = 4.dp)
            )
            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = coupon.cashback.toString(),
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.White,
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
                        color = Color.White.copy(.7f),
                        lineHeight = 20.sp,
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Text(
                    text = "满${coupon.miniAmount}元可用",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(.7f),
                )
            }
        }
    }
}


@Composable
fun DiscountCoupon(modifier: Modifier = Modifier, coupon: Coupon, onClick: (() -> Unit)?) {
    Box(
        modifier = modifier
            .couponModifier()
            .let { if (onClick != null) it.clickable { onClick() } else it }
    ) {
        CouponBackground(
            colors = listOf(
                Color(0xFFe3a3ff),
                Color(0xFFaa80ec)
            ),
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingBag,
                contentDescription = null,
                tint = Color(0xFF914cec),
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp), horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "折扣券",
                color = Color.White,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.Black.copy(.3f))
                    .padding(horizontal = 4.dp)
            )
            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = (coupon.discount).toString(),
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.White,
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
                        color = Color.White.copy(.7f),
                        lineHeight = 20.sp,
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Text(
                    text = "满${coupon.miniAmount}元可用",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(.7f),
                )
            }
        }
    }
}

private fun Modifier.couponModifier() = composed {
    this
        .shadow(
            4.dp,
            MaterialTheme.shapes.extraSmall,
        )
        .clip(MaterialTheme.shapes.extraSmall)
        .clipToBounds()


}

@Preview(device = "spec:width=200dp,height=100dp,dpi=440")
@Composable
fun DiscountCouponPreview() {
    val coupon = Coupon(
        cashback = 0,
        discount = 85,
        expiryDate = "2023-05-31",
        id = 12345,
        miniAmount = 1000,
        name = "20% off on all items",
        startDate = "2023-05-01",
        storeId = 67890,
        type = 1,
        usageInstructions = "Enter coupon code at checkout to receive 20% off on all items."
    )
    DiscountCoupon(coupon = coupon, onClick = {})
}


@Preview(device = "spec:width=200dp,height=100dp,dpi=440")
@Composable
fun CashCouponPreview() {
    val coupon = Coupon(
        cashback = 500,
        discount = 20,
        expiryDate = "2023-05-31",
        id = 12345,
        miniAmount = 1000,
        name = "20% off on all items",
        startDate = "2023-05-01",
        storeId = 67890,
        type = 1,
        usageInstructions = "Enter coupon code at checkout to receive 20% off on all items."
    )
    CashCoupon(coupon = coupon, onClick = {})
}
