package feature.store_detail

import LoadUIStateScaffold
import SharedTopBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreDetailScreen(component: StoreDetailComponent) {
    val loadStoreUIState by component.modelState.loadStoreUIStateFlow.collectAsState()
    LoadUIStateScaffold(loadStoreUIState, onReload = { component.modelState.loadStoreDetail() }) { store ->
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                SharedTopBar(
                    modifier = Modifier.background(Color.Transparent),
                    smallTitle = { Text("Small Title") },
                    bigTitle = {
                        Box(modifier = Modifier.fillMaxSize().background(Color.Red))
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.Default.ArrowLeft,
                                contentDescription = null
                            )
                        }
                    },
                    actionBar = {},
                    scrollBehavior = scrollBehavior
                )
            }
        ) { padding ->
            LazyColumn(modifier = Modifier.padding(padding)) {
                repeat(1000) {
                    item { Text(it.toString()) }
                }
            }
        }
    }
}