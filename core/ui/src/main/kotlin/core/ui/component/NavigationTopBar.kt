package core.ui.component

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.arkivanov.decompose.router.stack.pop
import core.common.navigation.rootNavigation
import core.design_system.Icons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigationClick: () -> Unit = { rootNavigation.pop() }
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(painterResource(Icons.caretLeft), contentDescription = null)
            }
        }
    )
}