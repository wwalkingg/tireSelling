package core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import core.common.baseUrl
import core.model.Coach

@Composable
fun CoachCard(modifier: Modifier = Modifier, coach: Coach, onClick: () -> Unit) {
    BoxWithConstraints(modifier = modifier) {
        val maxWith = maxWidth
        AsyncImage(
            model = baseUrl + coach.avatar,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(maxWith * 0.4f),
            contentScale = ContentScale.Crop
        )
        Text("教练:${coach.name}", modifier = Modifier.align(Alignment.BottomStart))
    }
}