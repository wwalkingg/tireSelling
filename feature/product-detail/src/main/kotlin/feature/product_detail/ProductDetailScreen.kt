package feature.product_detail

import DialogContent
import LoadUIStateScaffold
import PostUIStateDialog
import SmallLoadUIStateScaffold
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import components.RecommendProducts
import core.common.Config
import core.common.NavConfig
import core.common.navigation
import core.component_base.PostUIState
import kotlinx.collections.immutable.toPersistentList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(component: ProductDetailComponent) {
    val loadProductDetailUIState by component.modelState.loadProductDetailUIStateFlow.collectAsState()
    var isShopCarVisible by remember { mutableStateOf(false) }
    var isBuySheetVisible by remember {
        mutableStateOf(
            false
        )
    }
    LoadUIStateScaffold(
        loadProductDetailUIState,
        onReload = { component.modelState.loadProductDetail() }) { productDetail ->
        Scaffold(
            bottomBar = {
                ProductDetailBottomBar(
                    price = productDetail.product.price,
                    onInsertCart = {
                        component.modelState.addToShopCar(productDetail.product)
                        isShopCarVisible = true
                    },
                    onBuy = { isBuySheetVisible = true }
                )
            }
        ) { padding ->
            val listState = rememberLazyListState()
            val firstVisibleItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
            val topBarTransparent by
            animateFloatAsState(if (firstVisibleItemIndex >= 1) 1f else 0f)
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                TopAppBar(
                    modifier = Modifier.zIndex(10f),
                    title = {
                        Text(productDetail.product.name, modifier = Modifier.alpha(topBarTransparent))
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { navigation.pop() },
                            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surface)

                        ) {
                            Icon(
                                Icons.Filled.KeyboardArrowLeft,
                                contentDescription = null,
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(
                            topBarTransparent
                        )
                    ),
                )
                LazyColumn(modifier = Modifier.fillMaxSize(), state = listState) {
                    item {
                        AsyncImage(
                            model = Config.baseImgUrl + productDetail.product.image,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .background(MaterialTheme.colorScheme.outlineVariant),
                            contentScale = ContentScale.Crop
                        )
                    }
                    item {
                        Text(
                            productDetail.product.name,
                            style = MaterialTheme.typography.displaySmall,
                            modifier = Modifier.padding(10.dp, 10.dp)
                        )
                    }
                    item {
                        Column {
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navigation.push(NavConfig.BrandDetail(productDetail.product.brandId)) }
                                .padding(10.dp, 3.dp)) {
                                Text(text = "品牌", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
                                Text(text = productDetail.product.brand, style = MaterialTheme.typography.bodyMedium)
                            }
                            Divider()
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navigation.push(NavConfig.ModelDetail(productDetail.product.modelId)) }
                                .padding(10.dp, 3.dp)) {
                                Text(text = "型号", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
                                Text(text = productDetail.product.model, style = MaterialTheme.typography.bodyMedium)
                            }
                            Divider()
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navigation.push(NavConfig.StoreDetail(productDetail.product.storeId)) }
                                .padding(10.dp, 3.dp)) {
                                Text(text = "店铺", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
                                Text(
                                    text = productDetail.store?.storeName ?: "自营",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Divider()
                        }
                    }
                    item {
                        Text(
                            text = "商品详情",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(10.dp, 3.dp)
                        )
                        Text(
                            text = "    ${productDetail.product.productDescription}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(10.dp, 4.dp),
                            lineHeight = 30.sp,
                        )
                    }

                    item {
                        val loadRecommendProductsUIState by component.modelState.loadRecommendProductUIStateFlow.collectAsState()
                        SmallLoadUIStateScaffold(loadUIState = loadRecommendProductsUIState) { products ->
                            RecommendProducts(modifier = Modifier.fillMaxWidth(), products.toPersistentList())
                        }

                    }
                }
                if (isShopCarVisible) {
                    ShopCarButtonSheet(onDismissRequest = { isShopCarVisible = false }) { productAndIntList, address, coupon ->
                        component.modelState.buy(productAndIntList, address, coupon)
                    }
                }
                ProductSum(
                    isVisible = isBuySheetVisible,
                    onDismissRequest = { isBuySheetVisible = false },
                    onBuy = { productAndIntList, address, coupon ->
                        component.modelState.buy(productAndIntList, address, coupon)
                        isBuySheetVisible = false
                    },
                    productAndNumbers = listOf(productDetail.product to 1).toPersistentList()
                )
            }
        }
    }

    val createOrderUIState by component.modelState.createOrderUIStateFlow.collectAsState()
    PostUIStateDialog(postUIState = createOrderUIState)
}

