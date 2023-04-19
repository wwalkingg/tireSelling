package feature.product_detail

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import com.example.android.core.model.Address
import io.ktor.websocket.*

@Composable
fun AddressSelectDialog(onDismissRequest: () -> Unit, onSelect: (Address) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Frame.Text("地址选择")
        },
        confirmButton = { }
    )
}