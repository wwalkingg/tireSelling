package feature.all_course

import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.pop
import core.common.navigation.rootNavigation
import core.design_system.Icons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopBar(
    modifier: Modifier = Modifier,
    orderMethods: OrderMethods,
    onOrderMethodClick: (OrderMethods) -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = "全部课程") },
        actions = {
            var isExpanded by remember {
                mutableStateOf(false)
            }
            IconButton(onClick = { isExpanded = true }) {
                Icon(
                    painterResource(Icons.funnel),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "按照难度排序") },
                        trailingIcon = {
                            if (orderMethods == OrderMethods.Difficulty) Icon(
                                painter = painterResource(Icons.check),
                                contentDescription = null,
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        onClick = {
                            onOrderMethodClick(OrderMethods.Difficulty)
                            isExpanded = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "按照上传时间排序") },
                        trailingIcon = {
                            if (orderMethods == OrderMethods.UploadTime) Icon(
                                painter = painterResource(Icons.check),
                                contentDescription = null,
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        onClick = {
                            onOrderMethodClick(OrderMethods.UploadTime)
                            isExpanded = false
                        })
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = { rootNavigation.pop() }) {
                Icon(painterResource(Icons.caretLeft), contentDescription = null)
            }
        },
    )
}