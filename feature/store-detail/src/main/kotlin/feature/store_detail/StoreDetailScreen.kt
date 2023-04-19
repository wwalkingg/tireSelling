package feature.store_detail

import LoadUIStateScaffold
import SmallLoadUIStateScaffold
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import components.Comment
import core.common.NavConfig
import core.common.navigation

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun StoreDetailScreen(component: StoreDetailComponent) {
    val loadStoreUIState by component.modelState.loadStoreUIStateFlow.collectAsState()
    val loadStoreCommentsUIState by component.modelState.loadStoreCommentsUIStateFlow.collectAsState()
    val loadStoreProductsUIState by component.modelState.loadStoreProductsUIStateFlow.collectAsState()
    val loadStoreActivitiesUIState by component.modelState.loadStoreActivitiesUIStateFlow.collectAsState()
    LoadUIStateScaffold(loadStoreUIState, onReload = { component.modelState.loadStoreDetail() }) { store ->
        Scaffold { padding ->
            val listState = rememberLazyListState()
            val firstVisibleItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
            val topBarTransparent by
            animateFloatAsState(if (firstVisibleItemIndex >= 1) 1f else 0f)
            Box(modifier = Modifier.padding(padding).fillMaxSize()) {
                TopAppBar(
                    modifier = Modifier.zIndex(10f),
                    title = {
                        Text(store.name, modifier = Modifier.alpha(topBarTransparent))
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
                            model = store.logo,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth().aspectRatio(1f)
                                .background(MaterialTheme.colorScheme.outlineVariant),
                            contentScale = ContentScale.Crop
                        )
                    }
                    item {
                        Text(
                            store.name,
                            style = MaterialTheme.typography.displaySmall,
                            modifier = Modifier.padding(10.dp, 10.dp)
                        )
                    }
                    item {
                        Text(store.description, modifier = Modifier.padding(10.dp, 5.dp))
                    }
                    item {
                        SmallLoadUIStateScaffold(loadStoreProductsUIState) { products ->
                            Column {
                                Text(
                                    "产品列表",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(horizontal = 10.dp, 5.dp)
                                )
                                LazyRow(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp),
                                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                                ) {
                                    items(items = products, key = { it.id }) { product ->
                                        Box(
                                            modifier = Modifier.clip(MaterialTheme.shapes.medium)
                                                .size(200.dp, 160.dp)
                                                .clickable { navigation.push(NavConfig.ProductDetail(product.id)) }
                                        ) {
                                            AsyncImage(
                                                model = product.image,
                                                contentDescription = null,
                                                modifier = Modifier.fillMaxSize()
                                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                                contentScale = ContentScale.Crop
                                            )
                                            Row(
                                                modifier = Modifier.fillMaxWidth()
                                                    .align(Alignment.BottomCenter)
                                                    .drawTitleBackground()
                                                    .padding(10.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = product.name,
                                                    style = MaterialTheme.typography.titleLarge,
                                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    modifier = Modifier.weight(1f)
                                                )
                                                Text(
                                                    text = "CNY ${String.format("%.2f", product.price)}",
                                                    style = MaterialTheme.typography.labelMedium,
                                                    color = MaterialTheme.colorScheme.error,
                                                    fontWeight = FontWeight.Black,
                                                    maxLines = 1,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    item {
                        SmallLoadUIStateScaffold(loadStoreActivitiesUIState) { storeActivities ->
                            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSecondaryContainer) {
                                Column {
                                    Text(
                                        text = "活动",
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.padding(10.dp, 10.dp)
                                    )
                                    storeActivities.forEach {
                                        StoreActivity(modifier = Modifier.fillMaxWidth(), it)
                                    }
                                }
                            }
                        }
                    }
                    item {
                        var isAllCommentsVisible by remember { mutableStateOf(false) }
                        SmallLoadUIStateScaffold(loadStoreCommentsUIState) { comments ->
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
        }
    }
}


fun Modifier.drawTitleBackground(): Modifier = composed {
    val gray = MaterialTheme.colorScheme.outlineVariant
    this.drawBehind {
        drawRect(
            brush = Brush.verticalGradient(
                listOf(
                    gray.copy(0f),
                    gray
                )
            )
        )
    }
}