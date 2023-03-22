import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import feature.home.BottomMenu

internal class HomeModelState : ModelState() {
    @OptIn(ExperimentalFoundationApi::class)
    val pagerState = PagerState(0)
}