import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core.design_system.Icons
import components.Comment
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

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
                onCollectClick = component.modelState::collectProduct
            )
        }) {
            Column(modifier = Modifier.padding(it).fillMaxSize().verticalScroll(rememberScrollState())) {
                AsyncImage(
                    model = product.image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().aspectRatio(1.4f).background(Color.Black.copy(.3f)),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp).padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        product.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "CNY ${String.format("%.2f", product.price)}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Divider()
                    Text(product.description, style = MaterialTheme.typography.bodyMedium)
                    AssistChip(
                        onClick = { /*todo*/ },
                        label = { Text(store.name) },
                        trailingIcon = { Icon(painterResource(Icons.store), contentDescription = null) }
                    )
                    Card {
                        Column(modifier = Modifier.padding(10.dp,0.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("评价", style = MaterialTheme.typography.titleLarge)
                                TextButton(onClick = {}) {
                                    Text("查看全部")
                                }
                            }
                            Column(
                                modifier = Modifier,
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                repeat(10) {
                                    Comment(
                                        modifier = Modifier.fillMaxWidth(),
                                        avatar = "",
                                        username = "test",
                                        content = "test",
                                        datetime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
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