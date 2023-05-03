import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.push
import components.ProductItem
import core.common.NavConfig
import core.common.navigation

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchResultScreen(modifier: Modifier = Modifier, component: SearchResultComponent) {
    Scaffold(topBar = { NavigationTopBar(title = "搜索") }) { padding ->
        val loadSortUIState by component.modelState.loadSortUIStateFlow.collectAsState()
        LoadUIStateScaffold(modifier = Modifier.padding(padding), loadUIState = loadSortUIState) { products ->
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                stickyHeader {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimaryContainer) {
                                BasicTextField(
                                    value = component.modelState.keyword,
                                    onValueChange = { component.modelState.keyword = it },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(vertical = 10.dp),
                                    textStyle = MaterialTheme.typography.bodyMedium
                                )
                                IconButton(onClick = {
                                    component.modelState.loadSort(component.modelState.keyword)
                                }) {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = null,
                                    )
                                }
                            }
                        }
                    }
                }
                items(items = products) { product ->
                    ProductItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navigation.push(NavConfig.ProductDetail(product.id)) }, product = product
                    )
                }
            }
        }

    }

}