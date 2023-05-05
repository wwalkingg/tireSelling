package components

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android.core.model.CarStore
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CarStore(modifier: Modifier = Modifier, carStore: CarStore) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier.weight(1f)) {
            Text(text = carStore.name, style = MaterialTheme.typography.titleLarge)
            Text(text = carStore.address, style = MaterialTheme.typography.labelMedium, color = Color.Black.copy(.5f))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = carStore.phone, style = MaterialTheme.typography.labelSmall, color = Color(0xff005706))
        }
        val context = LocalContext.current
        val callPermission = rememberPermissionState(permission = Manifest.permission.CALL_PHONE)
        IconButton(
            onClick = {
                if (callPermission.status.isGranted) {
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + carStore.phone))
                    with(context) {
                        startActivity(intent)
                    }
                } else {
                    callPermission.launchPermissionRequest()
                }
            }
        ) {
            Icon(imageVector = Icons.Default.Call, contentDescription = null, tint = Color(0xff005706))
        }
    }
}

@Preview
@Composable
fun CarStorePreview() {
    val carStore = CarStore.stores.first()
    CarStore(modifier = Modifier.size(500.dp, 80.dp), carStore = carStore)
}