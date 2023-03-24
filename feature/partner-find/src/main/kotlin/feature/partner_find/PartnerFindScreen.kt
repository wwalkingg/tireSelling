package feature.partner_find

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import core.common.navigation.Config
import core.common.navigation.rootNavigation
import core.design_system.Icons
import core.design_system.component.loading
import core.ui.component.PartnerCard
import core.ui.status_page.ErrorPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartnerFindScreen(modifier: Modifier = Modifier, component: PartnerFindComponent) {
    val loadMyPartnerUIState by component.modelState.loadMyPartnerUIState.collectAsState()
    Scaffold(modifier, topBar = { TopBar() }) { paddingValues ->
        when (loadMyPartnerUIState) {
            LoadMyPartnerUIState.Error -> ErrorPage { component.modelState.loadMyPartner() }
            LoadMyPartnerUIState.Loading -> Box(Modifier.fillMaxSize().loading())
            is LoadMyPartnerUIState.Success -> {
                Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                    Text("我的伙伴", style = MaterialTheme.typography.titleLarge)
                    val list = (loadMyPartnerUIState as LoadMyPartnerUIState.Success).data
                    LazyHorizontalGrid(rows = GridCells.Fixed(3)) {
                        items(list.size) {
                            PartnerCard(partnerSimple = list[it], onClick = {

                            })
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    TopAppBar(title = {
        Text("我的伙伴")
    }, navigationIcon = {
        IconButton(onClick = { rootNavigation.pop() }) {
            Icon(painterResource(Icons.caretLeft), contentDescription = null)
        }
    })
}