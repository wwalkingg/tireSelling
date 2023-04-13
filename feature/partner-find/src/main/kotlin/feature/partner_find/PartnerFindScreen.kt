package feature.partner_find

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import core.common.baseUrl
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
                Column(modifier = Modifier.padding(paddingValues).padding(horizontal = 10.dp).fillMaxSize()) {
                    val list = (loadMyPartnerUIState as LoadMyPartnerUIState.Success).data
                    if (list.isEmpty()) {
                        Box(
                            modifier = Modifier.clip(CircleShape).fillMaxWidth().height(100.dp)
                                .background(MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = "还没添加伙伴",
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.displayMedium
                            )
                        }
                    } else {
                        Text("我的伙伴", style = MaterialTheme.typography.titleLarge)
                        Row {
                            list.forEach {
                                PartnerCard(partnerSimple = it, onClick = {})
                            }
                        }
                    }

                    val loadRecommendPartnerUIState by component.modelState.loadRecommendPartnerUIStateFlow.collectAsState()
                    when (loadRecommendPartnerUIState) {
                        LoadRecommendPartnerUIState.Error -> {
                            Text("Error")
                        }

                        LoadRecommendPartnerUIState.Loading -> {
                            Text("Loading")
                        }

                        is LoadRecommendPartnerUIState.Success -> {
                            val recommendPartners = (loadMyPartnerUIState as LoadMyPartnerUIState.Success).data
                            Text("推荐伙伴", style = MaterialTheme.typography.titleLarge)
                            Column {
                                recommendPartners.forEach {
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        AsyncImage(
                                            model = baseUrl + it.avatar,
                                            contentDescription = null,
                                            modifier = Modifier.size(50.dp)
                                        )
                                        Column {
                                            Text(it.name)
                                        }
                                    }
                                }
                            }
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