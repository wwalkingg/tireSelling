package feature.home.recommend

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.design_system.Icons
import core.design_system.Images
import core.model.SwiperResp
import feature.home.recommend.FunctionalMenu.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendScreen(modifier: Modifier = Modifier, component: RecommendComponent) {
    Scaffold(modifier, topBar = { TopBar { } }) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Swiper(
                modifier = Modifier.fillMaxWidth().height(140.dp),
                list = List(10) {
                    SwiperResp(
                        id = it,
                        imageUrl = "https://picsum.photos/800/600",
                        createTime = ""
                    )
                })
            FunctionalMenus(modifier = Modifier.padding(5.dp).fillMaxWidth(), onClick = {
                when (it) {
                    AllCourse -> {}
                    PersonalHealth -> {}
                    TODO -> {}
                    FindPartner -> {}
                }
            })
            Recommends(modifier = Modifier.padding(10.dp).fillMaxWidth())

        }
    }
}

@Composable
private fun TopBar(modifier: Modifier = Modifier, onSearchClick: () -> Unit) {
    Box(modifier = modifier.fillMaxWidth().height(50.dp).padding(8.dp)) {
        Image(
            painter = painterResource(Images.logo),
            contentDescription = null,
            modifier = Modifier.align(
                Alignment.Center
            ).fillMaxHeight().padding(4.dp)
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