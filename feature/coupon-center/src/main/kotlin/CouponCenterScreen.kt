import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.pop
import com.example.android.core.model.CouponPackage
import com.example.core.design_system.Images
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import components.CouponPackage
import components.CouponPackageBottomSheetWithReceive
import components.CouponWithDetailButtonSheet
import core.common.navigation
import core.component_base.LoadUIState
import core.component_base.PostUIState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun CouponCenterScreen(component: CouponCenterComponent) {
    val systemController = rememberSystemUiController()
    SideEffect {
        systemController.setStatusBarColor(
            color = Color(0xffAC76FF),
            darkIcons = true
        )
    }
    val loadAllCouponsUIState by component.modelState.loadAllCouponsUIStateFlow.collectAsState()
    val loadAllCouponPackagesUIState by component.modelState.loadAllCouponPackageUIStateFlow.collectAsState()
    var couponAndPackageCount: Int? = null
    if (loadAllCouponPackagesUIState is LoadUIState.Success && loadAllCouponsUIState is LoadUIState.Success) {
        couponAndPackageCount = (loadAllCouponPackagesUIState as LoadUIState.Success<List<CouponPackage>>).data.size
        (loadAllCouponPackagesUIState as LoadUIState.Success<List<CouponPackage>>).data.size
    }
    Scaffold(
        topBar = {
            Box(modifier = Modifier.height(IntrinsicSize.Max)) {
                Image(
                    painter = painterResource(id = Images.couponCenterBackgroundImage), contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                )
                Column(
                    modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { navigation.pop() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                    Column {
                        Text(
                            text = "优惠中心",
                            style = MaterialTheme.typography.displaySmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(10.dp)
                        )
                        AnimatedVisibility(visible = couponAndPackageCount != null) {
                            Text(
                                text = "有${couponAndPackageCount}张优惠券/卡包待领取",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }) {
        LazyColumn(Modifier.padding(it)) {
            item {
                Text(
                    "待领取优惠券",
                    style = MaterialTheme.typography.displaySmall.copy(
                        brush = couponTitleBrush
                    ),
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                SmallLoadUIStateScaffold(loadAllCouponsUIState) { coupons ->
                    Column {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(10.dp)
                        ) {
                            items(coupons) { coupon ->
                                CouponWithDetailButtonSheet(
                                    modifier = Modifier.size(160.dp, 80.dp),
                                    coupon,
                                    onReceiveClick = {
                                        component.modelState.receiveCoupon(coupon)
                                    })
                            }
                        }

                        CompositionLocalProvider(LocalContentColor provides Color.Black.copy(.5f)) {
                            Row(Modifier.padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Info, contentDescription = null)
                                Text(text = "优惠券可在[我的优惠券卡包]中查看", modifier = Modifier.padding(10.dp))
                            }
                        }
                    }
                }
            }
            item {
                Text(
                    "待领取卡包",
                    style = MaterialTheme.typography.displaySmall.copy(
                        brush = couponTitleBrush
                    ),
                    modifier = Modifier.padding(10.dp)
                )
                SmallLoadUIStateScaffold(
                    loadAllCouponPackagesUIState
                ) { couponPackages ->
                    Column {
                        Row(
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            couponPackages.forEach { couponPackage ->
                                var isVisible by remember {
                                    mutableStateOf(false)
                                }
                                Box {
                                    CouponPackage(
                                        modifier = Modifier.width(180.dp),
                                        couponPackage = couponPackage,
                                        onClick = { isVisible = true })
                                    if (isVisible) {
                                        CouponPackageBottomSheetWithReceive(
                                            isVisible = isVisible,
                                            onDismissRequest = { isVisible = false },
                                            couponPackage = couponPackage,
                                            onReceiveClick = {
                                                component.modelState.receiveCouponPackage(couponPackage)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                        CompositionLocalProvider(LocalContentColor provides Color.Black.copy(.5f)) {
                            Row(Modifier.padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Info, contentDescription = null)
                                Text(text = "卡包捆绑领取", modifier = Modifier.padding(10.dp))
                            }
                        }
                    }
                }
            }
        }
        val receiveCoupon by component.modelState.receiveCouponUIStateFlow.collectAsState()
        PostUIStateDialog(postUIState = receiveCoupon)
    }
}


val couponTitleBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFE94949),
        Color(0xFFF8B7B7),
    ),
    tileMode = androidx.compose.ui.graphics.TileMode.Clamp
)