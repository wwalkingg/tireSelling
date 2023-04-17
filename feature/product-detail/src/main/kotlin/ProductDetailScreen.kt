import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arkivanov.decompose.router.stack.pop
import com.example.android.core.model.Product
import core.common.navigation
import core.component_base.LoadUIState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProductDetailScreen(modifier: Modifier = Modifier, component: ProductDetailComponent) {
    val loadProductDetailUIState by component.modelState.loadProductDetailUIStateFlow.collectAsState()
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(component.modelState.snackBarState) },
        topBar = {
            Row {
                IconButton(
                    onClick = { navigation.pop() },
                    colors = IconButtonDefaults.iconButtonColors(Color.White)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        }) { padding ->
        Column(modifier = Modifier.padding(padding).verticalScroll(scrollState)) {
            when (loadProductDetailUIState) {
                is LoadUIState.Error -> {
                    Text(text = "Error")
                }

                LoadUIState.Loading -> {
                    Text(text = "Loading")
                }

                is LoadUIState.Loaded -> {
                    val product = loadProductDetailUIState as LoadUIState.Loaded<Product>
                    AsyncImage(
                        model = component.modelState.image,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().aspectRatio(1.4f).background(Color.Black.copy(.3f))
                    )
                    Column(modifier = Modifier.padding(horizontal = 10.dp).padding(top = 10.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(
                                    product.data.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Black
                                )
                                Text(
                                    text = "CNY ${String.format("%.2f", product.data.price)}",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                            IconButton(onClick = { component.modelState.collectProduct() }) {
                                val icon = if (component.modelState.isCollected) {
                                    painterResource(com.example.core.design_system.Icons.heatFill)
                                } else painterResource(com.example.core.design_system.Icons.heat)
                                Icon(
                                    painter = icon,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                        Spacer(Modifier.height(10.dp))
                        Text(product.data.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}