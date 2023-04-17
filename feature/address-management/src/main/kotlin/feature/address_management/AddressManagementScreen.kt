import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android.core.model.Address
import feature.address_management.AddAddressDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressManagementScreen(modifier: Modifier = Modifier, component: AddressManagementComponent) {
    var isAddAddressBottomSheetVisible by remember {
        mutableStateOf(false)
    }
    if (isAddAddressBottomSheetVisible) {
        AddAddressDialog(
            onDismissRequest = { isAddAddressBottomSheetVisible = false },
            onAdd = { component.modelState.requestToAddAddress(it) })
    }
    var editAddress by remember { mutableStateOf<Address?>(null) }
    if (editAddress != null) {
        AddAddressDialog(
            onDismissRequest = { editAddress = null },
            onAdd = { component.modelState.requestToAddAddress(it) }, address = editAddress,
            onDelete = {
                component.modelState.deleteAddress(it.id)
                editAddress = null
            }
        )
    }
    Scaffold(
        topBar = { NavigationTopBar(title = "地址管理") },
        bottomBar = { BottomBar { isAddAddressBottomSheetVisible = true } }
    ) { padding ->
        val addresses = component.modelState.addressStore.addresses
        Column(Modifier.padding(padding)) {
            addresses.forEach {
                Column(modifier = Modifier.fillMaxWidth().clickable { editAddress = it }
                    .padding(horizontal = 20.dp, vertical = 10.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(it.name, style = MaterialTheme.typography.titleMedium)
                        Text(it.phone, style = MaterialTheme.typography.labelSmall)
                    }
                    Spacer(Modifier.height(2.dp))
                    Text(it.address, style = MaterialTheme.typography.bodySmall)
                }
                Divider()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(modifier: Modifier = Modifier, onClick: () -> Unit) {
    BottomAppBar(modifier) {
        Button(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(), onClick = onClick
        ) {
            Text(text = "新建收货地址")
        }
    }
}


