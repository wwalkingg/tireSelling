package components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.android.core.model.CouponPackage

@OptIn(ExperimentalTextApi::class)
@Composable
fun CouponPackage(modifier: Modifier = Modifier, couponPackage: CouponPackage, onClick: () -> Unit) {
    val mergeModifier =
        modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .clickable { onClick() }
    Column(mergeModifier) {
        Text(
            couponPackage.name,
            style = MaterialTheme.typography.titleLarge.copy(brush = couponPackageTitleBrush),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(30.dp, 10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Box(modifier = Modifier.padding(30.dp)) {
            couponPackage.coupons.forEachIndexed { index, coupon ->
                Coupon(
                    modifier = Modifier
                        .size(160.dp, 100.dp)
                        .graphicsLayer {
                            transformOrigin = TransformOrigin(0.5f, 1f)
                            val scale = 1f - 0.05f * index
                            scaleX = scale
                            scaleY = scale
                            translationY = -35f * index
                        }
                        .zIndex(-index.toFloat()), coupon = coupon
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponPackageBottomSheetWithReceive(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    couponPackage: CouponPackage,
    onReceiveClick: (() -> Unit)?
) {
    if (isVisible) {
        ModalBottomSheet(onDismissRequest = onDismissRequest) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)) {
                Text(
                    text = couponPackage.name,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = couponPackage.describe,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Spacer(modifier = Modifier.width(10.dp))
                    couponPackage.coupons.forEach { coupon ->
                        CouponWithDetailButtonSheet(
                            modifier = Modifier.size(130.dp, 80.dp),
                            coupon = coupon,
                            null
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
                if (onReceiveClick != null) {
                    Row(Modifier.padding(10.dp)) {
                        Button(onClick = onReceiveClick) {
                            Text(text = "领取")
                        }
                    }
                }
            }
        }
    }
}

val couponPackageTitleBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFF350072),
        Color(0xFF076800),
        Color(0xFF5C4C00),
    ),
    start = Offset.Zero,
    end = Offset.Infinite,
    tileMode = TileMode.Clamp,
)
