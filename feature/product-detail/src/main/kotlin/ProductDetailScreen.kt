import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.arkivanov.decompose.router.stack.push
import com.example.core.design_system.Icons
import components.Comment
import core.common.NavConfig
import core.common.navigation
import core.component_base.LoadUIState
import kotlinx.datetime.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProductDetailScreen(modifier: Modifier = Modifier, component: ProductDetailComponent) {
    val loadProductDetailUIState by component.modelState.loadProductDetailUIStateFlow.collectAsState()
    LoadUIStateScaffold(
        loadProductDetailUIState, onReload = component.modelState::loadProductDetail
    ) { productAndStore ->
        val product = productAndStore.product
        val store = productAndStore.store
        Scaffold(snackbarHost = { SnackbarHost(component.modelState.snackBarState) }, bottomBar = {
            ProductDetailBottomBar(
                isCollected = component.modelState.isCollected,
                onCollectClick = component.modelState::collectProduct,
                onBuy = { /*todo*/ }
            )
        }) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = product.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.4f)
                        .background(Color.Black.copy(.3f)),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        product.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "CNY ${String.format("%.2f", product.price)}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Divider()
                    Text(product.description, style = MaterialTheme.typography.bodyMedium)
                    AssistChip(
                        onClick = { navigation.push(NavConfig.StoreDetail(product.storeId)) },
                        label = { Text(store.name) },
                        trailingIcon = {
                            Icon(
                                painterResource(Icons.store),
                                contentDescription = null
                            )
                        }
                    )
                    val loadCommentsUIState by component.modelState.loadCommentsUIStateFlow.collectAsState()
                    Card {
                        Column(
                            modifier = Modifier.padding(
                                start = 10.dp,
                                end = 10.dp,
                                top = 0.dp,
                                bottom = 10.dp
                            )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("评价", style = MaterialTheme.typography.titleLarge)
                                    if (loadCommentsUIState == LoadUIState.Loading) {
                                        val indicatorSize =
                                            with(LocalDensity.current) { LocalTextStyle.current.fontSize.toDp() }
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .padding(start = 5.dp)
                                                .size(indicatorSize)
                                        )
                                    }
                                    if (loadCommentsUIState is LoadUIState.Error) {
                                        Text(
                                            text = "加载失败",
                                            color = MaterialTheme.colorScheme.error,
                                            style = MaterialTheme.typography.labelMedium,
                                            modifier = Modifier.padding(start = 5.dp)
                                        )
                                    }
                                }
                                var isAllCommentsDialogVisible by remember {
                                    mutableStateOf(false)
                                }
                                if (loadCommentsUIState is LoadUIState.Loaded) {
                                    TextButton(onClick = { isAllCommentsDialogVisible = true }) {
                                        Text("查看全部")
                                    }
                                }
                                if (isAllCommentsDialogVisible) {
                                    Dialog(onDismissRequest = {
                                        isAllCommentsDialogVisible = false
                                    }) {
                                        Column(
                                            modifier = Modifier
                                                .padding(vertical = 10.dp)
                                                .clip(MaterialTheme.shapes.medium)
                                                .fillMaxWidth()
                                                .background(MaterialTheme.colorScheme.surface)
                                                .padding(10.dp)
                                                .verticalScroll(rememberScrollState())
                                        ) {
                                            Text(
                                                text = "所有评论",
                                                style = MaterialTheme.typography.displayMedium
                                            )
                                            if (loadCommentsUIState is LoadUIState.Loaded) {
                                                (loadCommentsUIState as LoadUIState.Loaded).data
                                                    .forEach {
                                                        Comment(
                                                            avatar = it.avatar,
                                                            username = it.name,
                                                            content = it.content,
                                                            datetime = it.createTime
                                                        )
                                                        Divider()
                                                    }
                                            }
                                            Button(onClick = {
                                                isAllCommentsDialogVisible = false
                                            }, modifier = Modifier.align(Alignment.End)) {
                                                Text(text = "确定")
                                            }
                                        }
                                    }
                                }
                            }
                            Column(
                                modifier = Modifier,
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                if (loadCommentsUIState is LoadUIState.Loaded) {
                                    (loadCommentsUIState as LoadUIState.Loaded).data.take(3)
                                        .forEach {
                                            Comment(
                                                avatar = it.avatar,
                                                username = it.name,
                                                content = it.content,
                                                datetime = it.createTime
                                            )
                                        }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}