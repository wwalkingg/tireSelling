package feature.home.recommend

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.push
import core.common.navigation.Config
import core.common.navigation.rootNavigation
import core.design_system.Icons
import core.design_system.Images
import core.design_system.component.loading
import core.model.SwiperResp
import core.ui.status_page.ErrorPage
import feature.home.recommend.FunctionalMenu.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendScreen(modifier: Modifier = Modifier, component: RecommendComponent) {
    Scaffold(modifier, topBar = {
        TopBar {
            rootNavigation.push(Config.RootConfig.Search)
        }
    }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Swiper(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                list = List(10) {
                    SwiperResp(
                        id = it,
                        imageUrl = "https://picsum.photos/800/600",
                        createTime = ""
                    )
                })
            FunctionalMenus(modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(), onClick = {
                when (it) {
                    AllCourse -> {
                        component.onCourseAllFunctionalMenuClick()
                    }

                    PersonalHealth -> {
                        component.onPersonHealthFunctionalMenuClick()
                    }

                    Coach -> {
                        component.onCoachAllFunctionalMenuClick()
                    }

                    FindPartner -> {
                        component.onPartnerFindFunctionalMenuClick()
                    }
                }
            })
            val loadRecommendUIState by component.modelState.loadRecommendUIStateFlow.collectAsState()
            when (loadRecommendUIState) {
                LoadRecommendUIState.Error -> ErrorPage { component.modelState.loadRecommend() }
                LoadRecommendUIState.Loading -> Box(Modifier.fillMaxSize().loading())
                is LoadRecommendUIState.Success -> {
                    Recommends(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        (loadRecommendUIState as LoadRecommendUIState.Success).data
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(modifier: Modifier = Modifier, onSearchClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(Images.logo),
            contentDescription = null,
            modifier = Modifier
                .align(
                    Alignment.Center
                )
                .fillMaxHeight()
                .padding(4.dp)
        )
        IconButton(onClick = onSearchClick, modifier = Modifier.align(Alignment.CenterEnd)) {
            Icon(
                painter = painterResource(Icons.magnifyingGlass),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}