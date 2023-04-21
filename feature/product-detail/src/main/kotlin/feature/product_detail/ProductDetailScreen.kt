package feature.product_detail

import LoadUIStateScaffold
import LoadingDialog
import SmallLoadUIStateScaffold
import SuccessDialog
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeatPump
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import components.Comment
import core.common.Config
import core.common.NavConfig
import core.common.navigation
import core.component_base.PostUIState
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProductDetailScreen(component: ProductDetailComponent) {
    val loadProductDetailUIState by component.modelState.loadProductDetailUIStateFlow.collectAsState()
    val loadCommentsUIState by component.modelState.loadCommentsUIStateFlow.collectAsState()
    LoadUIStateScaffold(
        loadProductDetailUIState,
        onReload = { component.modelState.loadProductDetail() }) { productAndStore ->
        var isBuySheetVisible by remember {
            mutableStateOf(
                false
            )
        }
        Scaffold(
            bottomBar = {
                val context = LocalContext.current
                ProductDetailBottomBar(
                    isCollected = productAndStore.product.isFavorite,
                    onCollectClick = { component.modelState.collectProduct(context) },
                    price = productAndStore.product.price,
                    onBuy = { isBuySheetVisible = true }
                )
            }
        ) { padding ->
            val listState = rememberLazyListState()
            val firstVisibleItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
            val topBarTransparent by
            animateFloatAsState(if (firstVisibleItemIndex >= 1) 1f else 0f)
            Box(modifier = Modifier.padding(padding).fillMaxSize()) {
                TopAppBar(
                    modifier = Modifier.zIndex(10f),
                    title = {

                        Text(productAndStore.product.name, modifier = Modifier.alpha(topBarTransparent))
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
                            model = Config.baseImgUrl+productAndStore.product.image,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth().aspectRatio(1f)
                                .background(MaterialTheme.colorScheme.outlineVariant),
                            contentScale = ContentScale.Crop
                        )
                    }
                    item {
                        Text(
                            productAndStore.product.name,
                            style = MaterialTheme.typography.displaySmall,
                            modifier = Modifier.padding(10.dp, 10.dp)
                        )
                    }
                    item {
                        Text(productAndStore.store.description, modifier = Modifier.padding(10.dp, 5.dp))
                    }
                    item {
                        AssistChip(
                            modifier = Modifier.padding(10.dp),
                            onClick = { navigation.push(NavConfig.StoreDetail(productAndStore.store.id)) },
                            label = { Text(productAndStore.store.name) })
                    }
                    item {
                        var isAllCommentsVisible by remember { mutableStateOf(false) }
                        SmallLoadUIStateScaffold(loadCommentsUIState) { comments ->
                            Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("店铺评价", style = MaterialTheme.typography.titleLarge)
                                    TextButton(onClick = { isAllCommentsVisible = true }) {
                                        Text("查看全部")
                                    }
                                }
                                val firstFiveComments by remember { derivedStateOf { comments.take(5) } }
                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    firstFiveComments.forEach {
                                        Comment(
                                            modifier = Modifier.fillMaxWidth(),
                                            avatar = it.avatar, username = it.name,
                                            content = it.content,
                                            datetime = it.createTime
                                        )
                                    }
                                }
                            }
                            if (isAllCommentsVisible) {
                                Dialog(onDismissRequest = { isAllCommentsVisible = false }) {
                                    Column(
                                        modifier = Modifier.clip(MaterialTheme.shapes.medium)
                                            .background(MaterialTheme.colorScheme.surface)
                                            .padding(10.dp)
                                    ) {
                                        LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth()) {
                                            items(items = comments) {
                                                Comment(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    avatar = it.avatar, username = it.name,
                                                    content = it.content,
                                                    datetime = it.createTime
                                                )
                                            }
                                        }
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            Button(onClick = { isAllCommentsVisible = false }) {
                                                Text("确定")
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (isBuySheetVisible) {
                val addressList = remember { component.modelState.loadAddressList().toPersistentList() }
                var selectedAddress by remember { mutableStateOf(addressList.firstOrNull()) }
                ModalBottomSheet(onDismissRequest = { isBuySheetVisible = false }) {
                    Text(
                        "确认订单",
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Spacer(Modifier.height(10.dp))
                    Surface(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.extraLarge
                    ) {
                        Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max)) {
                            AsyncImage(
                                model = Config.baseImgUrl+productAndStore.product.image,
                                contentDescription = null,
                                modifier = Modifier.weight(3f).fillMaxHeight(),
                                contentScale = ContentScale.FillHeight
                            )
                            Spacer(Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(9f)) {
                                Text(productAndStore.product.name, style = MaterialTheme.typography.titleLarge)
                                Text(
                                    String.format("CNY %.2f", productAndStore.product.price),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }

                    Text(
                        text = "合计: CNY ${String.format("%.2f", productAndStore.product.price)}",
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

                    Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.Center) {
                        Button(onClick = {
                            component.modelState.buy(
                                productAndStore.store.id,
                                productAndStore.product.id,
                                productAndStore.product.price.toFloat(),
                                selectedAddress?.address ?: ""
                            )
                        }, modifier = Modifier.fillMaxWidth(.8f)) {
                            Text("结算")
                        }
                    }
                    if (isAddressSelectDialogVisible) {
                        AddressSelectDialog(
                            onDismissRequest = { isAddressSelectDialogVisible = false },
                            addressList = addressList,
                            selectedAddress = selectedAddress,
                            onSelect = { selectedAddress = it },
                        )
                    }
                    val createOrderUIState by component.modelState.createOrderUIStateFlow.collectAsState()
                    val context = LocalContext.current
                    when (createOrderUIState) {
                        is PostUIState.Error -> {
                            Toast.makeText(context, "下单失败", Toast.LENGTH_SHORT).show()
                        }

                        PostUIState.Loading -> {
                            LoadingDialog()
                        }

                        PostUIState.None -> {}
                        PostUIState.Success -> {
                            SuccessDialog()
                            LaunchedEffect(Unit){
                                delay(1000L)
                                navigation.push(NavConfig.OrderManagement)
                            }
                        }
                    }
                }
            }
        }
    }
}

