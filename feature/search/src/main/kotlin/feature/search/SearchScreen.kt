import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.pop
import core.common.navigation.rootNavigation
import core.design_system.component.loading
import core.ui.status_page.ErrorPage
import feature.search.SearchComponent
import feature.search.SearchResultContent
import feature.search.SearchState
import kotlinx.collections.immutable.toPersistentSet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    component: SearchComponent,
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("搜索") },
            navigationIcon = {
                IconButton(onClick = { rootNavigation.pop() }) {
                    Icon(
                        painterResource(core.design_system.Icons.caretLeft),
                        contentDescription = null
                    )
                }
            })
    }) {
        val searchState by component.modelState.searchState.collectAsState()
        Column(modifier.padding(it).padding(horizontal = 10.dp)) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = component.modelState.searchValue,
                onValueChange = { component.modelState.searchValue = it },
                trailingIcon = {
                    IconButton(onClick = { component.modelState.search() }) {
                        Icon(Icons.Default.Search, contentDescription = null)
                    }
                },
                placeholder = { Text("请输入关键字") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedBorderColor = Color.Unspecified,
                )
            )
            FilterChips(component.modelState.selectedTypes) {
                component.modelState.selectedTypes = it.toPersistentSet()
            }
            when (searchState) {
                SearchState.Error -> ErrorPage(onRefreshClick = { component.modelState.search() })
                SearchState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize().loading())
                }

                is SearchState.Success -> {
                    SearchResultContent(
                        modifier = Modifier.fillMaxSize(),
                        searchResult = (searchState as SearchState.Success).data,
                        filterSearchTypes = component.modelState.selectedTypes
                    )
                }
            }
        }
    }
}

