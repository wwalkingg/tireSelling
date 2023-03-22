package feature.home.recommend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.design_system.Icons

@Composable
internal fun Recommends(modifier: Modifier = Modifier) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painterResource(Icons.bookmarksDuotone), tint = Color(0xffe72d22), contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("热门推荐")
        }
    }
}