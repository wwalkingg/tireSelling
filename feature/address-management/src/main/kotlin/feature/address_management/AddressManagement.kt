
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        Column(Modifier.padding(padding)) {

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


