import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTopBar(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit,
    actionBar: () -> Unit,
    smallTitle: @Composable () -> Unit,
    bigTitle: @Composable () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    SideEffect {
        println("""
            ${scrollBehavior.state.contentOffset}
        """.trimIndent())
        if (scrollBehavior.state.heightOffsetLimit != 80f) {
            scrollBehavior.state.heightOffsetLimit = 80f
        }
    }
    LaunchedEffect(Unit){

    }
    val modifier0 = modifier.heightIn(min = 40.dp, max = 100.dp)
    Box(modifier0) {
        bigTitle()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun P() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(topBar = {
        SharedTopBar(
            modifier = Modifier,
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
    }, modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
        LazyColumn(modifier = Modifier.padding(it)) {
            repeat(1000) {
                item { Text(it.toString()) }
            }
        }
    }
}