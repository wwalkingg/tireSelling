import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Address
import core.component_base.ModelState
import core.datastore.AddressStore
import kotlinx.coroutines.launch

class AddressManagementComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext {
    internal val modelState = AddressManagementModelState()
}

internal class AddressManagementModelState : ModelState() {
    val snackBarFlow = SnackbarHostState()
    var addressStore by mutableStateOf(AddressStore.retrieve())
        private set

    fun reloadAddress() {
        addressStore = AddressStore.retrieve()
    }

    fun addAddress(address: Address) {
        addressStore = addressStore.copy(addresses = addressStore.addresses + address)
        coroutineScope.launch {
            addressStore.store()
        }
    }

    fun requestToAddAddress(address: Address): Boolean {
        if (checkInformation(address)) {
            addAddress(address)
            return true
        }
        return false
    }

    fun checkInformation(address: Address): Boolean {
        if (address.name.isEmpty()) {
            coroutineScope.launch { snackBarFlow.showSnackbar(message = "请输入收货人姓名") }
            return false
        }
        if (address.phone.isEmpty()) {
            coroutineScope.launch { snackBarFlow.showSnackbar(message = "请输入收货人电话") }
            return false
        }
        if (address.address.isEmpty()) {
            coroutineScope.launch {
                snackBarFlow.showSnackbar(message = "请输入收货人地址")
            }
            return false
        }
        if (address.detailAddress.isEmpty()) {
            coroutineScope.launch {
                snackBarFlow.showSnackbar(message = "请输入收货人详细地址")
            }
            return false
        }
        return true
    }

    fun deleteAddress(id: Long) {
        addressStore = addressStore.copy(addresses = addressStore.addresses.filterNot { it.id == id })
        coroutineScope.launch {
            addressStore.store()
        }
    }

}
