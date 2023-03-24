import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FilterChips(
    selectedTypes: Set<FilterSearchTypes>,
    onSelectedTypesChange: (Set<FilterSearchTypes>) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        FilterSearchTypes.values().forEach {
            val isSelected = selectedTypes.contains(it)
            ElevatedFilterChip(
                modifier = Modifier.weight(1f),
                selected = isSelected,
                label = { Text(it.uiName,style = MaterialTheme.typography.labelSmall) },
                onClick = { onSelectedTypesChange(if (isSelected) selectedTypes - it else selectedTypes + it) },
                leadingIcon = { Icon(Icons.Default.Check, contentDescription = null,modifier = Modifier.size(12.dp)) })
        }
    }
}

enum class FilterSearchTypes(val uiName: String) {
    Course("课程"),
    Coach("教练"),
    Record("记录"),
    Partner("用户")
}