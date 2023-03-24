package core.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import core.common.baseUrl
import core.model.Coach
import kotlin.math.roundToInt

@Composable
fun CoachSquareCard(modifier: Modifier = Modifier, coach: Coach, onClick: () -> Unit) {
    Column(modifier) {
        Box(Modifier.clip(MaterialTheme.shapes.medium).fillMaxWidth().weight(7f)){
            AsyncImage(
                model = baseUrl + coach.avatar,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Text("${coach.name}·${coach.workYear}年教练")
    }
}

@Preview
@Composable
private fun CoachSquareCardPreview() {
    CoachSquareCard(
        modifier = Modifier.size(100.dp, 120.dp),
        coach = Coach(
            id = 2,
            age = 32,
            sex = 1,
            name = "David Lee",
            avatar = "https://i.imgur.com/BYsOfL3.png",
            telephone = "+1 (555) 555-1234",
            introduce = "I specialize in strength and conditioning training.",
            workYear = 8
        ),
        onClick = {}
    )
}