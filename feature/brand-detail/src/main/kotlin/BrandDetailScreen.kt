import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arkivanov.decompose.router.stack.push
import com.example.android.core.model.Product
import core.common.Config
import core.common.NavConfig
import core.common.navigation

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BrandDetailScreen(modifier: Modifier = Modifier, component: BrandDetailComponent) {
    val loadBrandDetailUIState by component.modelState.loadBrandDetailUIStateFlow.collectAsState()
    LoadUIStateScaffold(loadUIState = loadBrandDetailUIState) { brandDetail ->
        Scaffold(topBar = { NavigationTopBar(title = "${brandDetail.brand.brandName} - ${brandDetail.brand.brandNumber}") }) { padding ->
            LazyColumn(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                stickyHeader {
                    Column(Modifier.background(MaterialTheme.colorScheme.surface).padding(10.dp)) {
                        Text(text = "品牌国家", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
                        Text(text = brandDetail.brand.country, style = MaterialTheme.typography.bodyLarge)
                        Divider(Modifier.padding(vertical = 10.dp))
                        Text(text = "品牌介绍", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
                        Text(text = brandDetail.brand.introduce, style = MaterialTheme.typography.bodyLarge)
                        Divider(Modifier.padding(vertical = 10.dp))
                        Text(text = "收录产品", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
                    }
                }
                items(items = brandDetail.products) { product ->
                    ProductItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navigation.push(NavConfig.ProductDetail(product.id)) }, product = product
                    )
                    Divider()
                }

            }
        }
    }
}


@Composable
fun ProductItem(modifier: Modifier = Modifier, product: Product) {
    val mergeModifier = modifier.padding(10.dp)
    Column(mergeModifier) {
        Row(modifier = Modifier.height(IntrinsicSize.Max)) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = product.productDescription,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "${product.brand} - ${product.model}", maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            AsyncImage(
                model = Config.baseImgUrl + product.image, contentDescription = null, modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .background(Color.Gray.copy(.5f))
            )
        }
    }
}