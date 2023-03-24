package core.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import core.model.PartnerSimple

@Composable
fun PartnerCard(modifier: Modifier = Modifier, partnerSimple: PartnerSimple, onClick: () -> Unit) {
    BoxWithConstraints(modifier.clickable { onClick() }) {
        val maxWidth = maxWidth
        Column(modifier = Modifier.fillMaxWidth().height(maxWidth * 0.6f)) {
            AsyncImage(
                model = partnerSimple.avatar,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().weight(7f)
            )
            Text(partnerSimple.name, modifier = Modifier.padding(5.dp).weight(3f))
        }
    }
}
