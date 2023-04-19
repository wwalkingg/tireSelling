package feature.store_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.android.core.model.StoreActivity

@Composable
fun StoreActivity(modifier: Modifier = Modifier, activity: StoreActivity, onClick: () -> Unit) {
    Row(
        modifier = modifier.padding(10.dp, 5.dp)
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable { onClick() }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                activity.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Spacer(Modifier.height(5.dp))
            Row(modifier = Modifier.height(IntrinsicSize.Max)) {
                ActivityDateTimeIcon(
                    modifier = Modifier.width(20.dp).fillMaxHeight()
                )
                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text(
                        activity.startTime,
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        activity.startTime,
                        style = MaterialTheme.typography.labelSmall
                    )
                }

            }
        }
        IconButton(
            onClick = onClick,
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
}