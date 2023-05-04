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
fun ModelDetailScreen(modifier: Modifier = Modifier, component: ModelDetailComponent) {

    val loadModelDetailUIState by component.modelState.loadModelDetailUIStateFlow.collectAsState()
    LoadUIStateScaffold(loadUIState = loadModelDetailUIState) { modelDetail ->
        Scaffold(topBar = { NavigationTopBar(title = "${modelDetail.model.modelName} - ${modelDetail.model.modelNumber}") }) { padding ->
            LazyColumn(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                stickyHeader {
                    Column(Modifier.background(MaterialTheme.colorScheme.surface).padding(10.dp)) {
                        Text(text = "适用", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
                        Text(text = modelDetail.model.compatibleVehicles, style = MaterialTheme.typography.bodyLarge)
                        Divider(Modifier.padding(vertical = 10.dp))
                        Text(text = "收录产品", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
                    }
                }
                items(items = modelDetail.products) { product ->
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