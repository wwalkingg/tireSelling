package core.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import core.model.Record

@Composable
fun RecordCard(modifier: Modifier = Modifier, record: Record, onClick: () -> Unit) {
    Column(modifier = modifier.clip(MaterialTheme.shapes.medium).clickable { onClick() }) {
        Text(record.content, style = MaterialTheme.typography.titleMedium)
        Text(record.createTime, style = MaterialTheme.typography.labelSmall)
    }
}