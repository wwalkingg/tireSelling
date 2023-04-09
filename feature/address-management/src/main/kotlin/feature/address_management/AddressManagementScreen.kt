
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.datastore.SettingStore
import feature.address_management.AddAddressDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressManagementScreen(modifier: Modifier = Modifier, component: AddressManagementComponent) {
    var isAddAddressBottomSheetVisible by remember {
        mutableStateOf(false)
    }
    if (isAddAddressBottomSheetVisible) {
        AddAddressDialog(onDismissRequest = {isAddAddressBottomSheetVisible = false}, onAdd = {})
    }
    Scaffold(
        topBar = { NavigationTopBar(title = "地址管理") },
        bottomBar = { BottomBar { isAddAddressBottomSheetVisible = true } }
    ) { padding ->
        val addresses by
        Column(Modifier.padding(padding)) {
            addresses.forEach {
                Card {
                    Text(it.address)
                }
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


