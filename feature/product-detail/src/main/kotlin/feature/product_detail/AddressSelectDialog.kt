package feature.product_detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.android.core.model.Address
import com.example.android.core.model.CarStore
import components.CarStore
import io.ktor.websocket.*

@Composable
fun AddressSelectDialog(
    onDismissRequest: () -> Unit,
    selectedAddress: Address?,
    onSelect: (Address) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text("门店选择")
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                CarStore.stores.forEach { carStore ->
                    CarStore(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSelect(
                                    Address(
                                        id = (0..Long.MAX_VALUE).random(),
                                        name = carStore.name,
                                        phone = carStore.phone,
                                        address = carStore.address,
                                        detailAddress = ""
                                    )
                                )
                            }, carStore = carStore
                    )
                    Divider()
                }

            }
        },
        confirmButton = {
            Button(onClick = { onDismissRequest() }) {
                Text("确定")
            }
        }
    )
}