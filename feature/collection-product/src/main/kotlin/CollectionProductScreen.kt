import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arkivanov.decompose.router.stack.push
import com.example.core.design_system.Icons
import components.drawTitleBackground
import core.common.Config
import core.common.NavConfig
import core.common.navigation

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CollectionProductScreen(modifier: Modifier = Modifier, component: CollectionProductComponent) {
//    val loadUIState by component.modelState.loadCollectedProductsUIStateFlow.collectAsState()
//    LoadUIStateScaffold(loadUIState) { products ->
//        val filterProducts by remember(products) { derivedStateOf { products.filterNot { it.id in component.modelState.canceledList } } }
//        Scaffold(topBar = { NavigationTopBar(title = "收藏商品") }) { padding ->
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(2),
//                Modifier.padding(padding),
//                contentPadding = PaddingValues(10.dp),
//                verticalArrangement = Arrangement.spacedBy(10.dp),
//                horizontalArrangement = Arrangement.spacedBy(10.dp)
//            ) {
//                items(items = filterProducts, key = {it.id}) { product ->
//                    Box(
//                        modifier = Modifier.clip(MaterialTheme.shapes.medium)
//                            .size(200.dp, 160.dp)
//                            .animateItemPlacement()
//                            .clickable { navigation.push(NavConfig.ProductDetail(product.id)) }
//                    ) {
//                        AsyncImage(
//                            model = Config.baseImgUrl + product.image,
//                            contentDescription = null,
//                            modifier = Modifier.fillMaxSize()
//                                .background(MaterialTheme.colorScheme.primaryContainer),
//                            contentScale = ContentScale.Crop
//                        )
//                        Row(
//                            modifier = Modifier.fillMaxWidth()
//                                .align(Alignment.BottomCenter)
//                                .drawTitleBackground()
//                                .padding(10.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Text(
//                                text = product.name,
//                                style = MaterialTheme.typography.titleLarge,
//                                color = MaterialTheme.colorScheme.onPrimaryContainer,
//                                maxLines = 1,
//                                overflow = TextOverflow.Ellipsis,
//                                modifier = Modifier.weight(1f)
//                            )
//                            Text(
//                                text = "CNY ${String.format("%.2f", product.price)}",
//                                style = MaterialTheme.typography.labelMedium,
//                                color = MaterialTheme.colorScheme.error,
//                                fontWeight = FontWeight.Black,
//                                maxLines = 1,
//                            )
//                        }
//                        Box(modifier = Modifier.align(Alignment.TopEnd)) {
//                            val context = LocalContext.current
//                            var isCollected by remember { mutableStateOf(product.isFavorite) }
//                            IconButton(onClick = {
//                                isCollected = !isCollected
//                                component.modelState.cancelCollectProduct(context, product.id)
//                            }) {
//                                val icon = painterResource(Icons.heatFill)
//                                Icon(
//                                    painter = icon,
//                                    contentDescription = null,
//                                    tint = MaterialTheme.colorScheme.error
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
}




