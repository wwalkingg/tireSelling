package com.example.feature.home.coupons_package

import SmallLoadUIStateScaffold
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.push
import components.CouponPackage
import components.CouponPackageBottomSheetWithReceive
import components.CouponWithDetailButtonSheet
import core.common.NavConfig
import core.common.navigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponsPackageScreen(component: CouponsPackageComponent) {
    Scaffold(floatingActionButton = {
        Column(horizontalAlignment = Alignment.End) {
            ExtendedFloatingActionButton(
                onClick = {
                    component.modelState.loadMyCoupons()
                    component.modelState.loadMyCouponPackages()
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            }
            Spacer(modifier = Modifier.height(20.dp))
            ExtendedFloatingActionButton(
                onClick = { navigation.push(NavConfig.CouponCenter) },
                containerColor = MaterialTheme.colorScheme.error
            ) {
                Text("前往领券中心", modifier = Modifier.padding(horizontal = 10.dp))
            }
        }
    }) { padding ->
        val loadMyCouponsUIState by component.modelState.loadMyCouponsUIStateFlow.collectAsState()
        val loadMyCouponPackagesUIState by component.modelState.loadMyCouponPackageUIStateFlow.collectAsState()
        LazyColumn(Modifier.padding(padding)) {
            item {
                Text("我的优惠券", style = MaterialTheme.typography.displaySmall, modifier = Modifier.padding(10.dp))
                SmallLoadUIStateScaffold(loadMyCouponsUIState, modifier = Modifier) { coupons ->
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(10.dp)
                    ) {
                        items(coupons) { coupon ->
                            CouponWithDetailButtonSheet(
                                modifier = Modifier
                                    .size(180.dp, 100.dp), coupon, onReceiveClick = null
                            )
                        }
                    }
                }
            }
            item {
                Text("我的卡包", style = MaterialTheme.typography.displaySmall, modifier = Modifier.padding(10.dp))
                SmallLoadUIStateScaffold(
                    loadMyCouponPackagesUIState,
                    modifier = Modifier.padding(10.dp)
                ) { couponPackages ->
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(couponPackages) { couponPackage ->
                            var isVisible by remember {
                                mutableStateOf(false)
                            }
                            CouponPackage(
                                modifier = Modifier.width(180.dp),
                                couponPackage = couponPackage,
                                onClick = { isVisible = true })
                            if (isVisible) {
                                CouponPackageBottomSheetWithReceive(
                                    isVisible = isVisible,
                                    onDismissRequest = { isVisible = false },
                                    couponPackage = couponPackage,
                                    onReceiveClick = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}