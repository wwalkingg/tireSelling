package feature.product_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.push
import com.example.android.core.model.Address
import core.common.NavConfig
import core.common.navigation
import io.ktor.websocket.*
import kotlinx.collections.immutable.PersistentList

@Composable
fun AddressSelectDialog(
    onDismissRequest: () -> Unit,
    addressList: PersistentList<Address>,
    selectedAddress: Address?,
    onSelect: (Address) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Frame.Text("地址选择")
        },
        text = {
            Column {
                addressList.forEach {
                    val isSelected = selectedAddress == it
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = isSelected, onClick = { onSelect(it) })
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(it.address)
                            Row(horizontalArrangement = Arrangement.Center) {
                                Text(it.name, style = MaterialTheme.typography.labelMedium)
                                Text(it.phone, style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                    Divider()
                }
            }
        },
        confirmButton = {
            Button(onClick = { onDismissRequest() }) {
                Text("确定")
            }
        },
        dismissButton = {
            TextButton(onClick = { navigation.push(NavConfig.AddressManagement) }) {
                Text("去添加地址")
            }
        }
    )
}