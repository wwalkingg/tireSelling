package feature.address_management

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.android.core.model.Address
import core.datastore.SettingStore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAddressDialog(onDismissRequest: () -> Unit, onAdd: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var name by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    var address by remember {
        mutableStateOf("")
    }
    var detailAddress by remember {
        mutableStateOf("")
    }
    var isQuitAlertDialogVisible by remember {
        mutableStateOf(false)
    }
    var isAddressSelectBottomSheetVisible by remember {
        mutableStateOf(false)
    }
    Dialog(onDismissRequest = {
        if (name.isNotEmpty() || phone.isNotEmpty() || address.isNotEmpty() || detailAddress.isNotEmpty()) {
            isQuitAlertDialogVisible = true
        } else {
            onDismissRequest()
        }
    }) {
        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surface)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = "添加地址", style = MaterialTheme.typography.displayMedium)
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "收货人") }, singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            TextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text(text = "手机号码") },
                prefix = { Text(text = "+86") },
                supportingText = { Text(text = "请输入正确的11位手机号码") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                )
            )
            TextField(
                value = address,
                onValueChange = { address = it },
                singleLine = true,
                label = { Text(text = "所在地区") },
                trailingIcon = {
                    IconButton(onClick = { isAddressSelectBottomSheetVisible = true }) {
                        Icon(imageVector = Icons.Default.List, contentDescription = null)
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)

            )
            TextField(
                value = detailAddress,
                onValueChange = { detailAddress = it },
                singleLine = true,
                label = { Text(text = "详细地址") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
            Button(
                onClick = {
                    scope.launch {
                        checkInformation(context, name, phone, address, detailAddress)
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "保存")
            }
            if (isAddressSelectBottomSheetVisible) {
                AddressSelectBottomSheet(onDismissRequest = {
                    isAddressSelectBottomSheetVisible = false
                })
            }
        }
    }
    if (isQuitAlertDialogVisible) {
        AlertDialog(
            onDismissRequest = { isQuitAlertDialogVisible = false },
            title = { Text(text = "是否放弃编辑？") },
            text = { Text(text = "您的编辑尚未保存，是否放弃编辑？") },
            confirmButton = {
                TextButton(onClick = { onDismissRequest() }) {
                    Text(text = "放弃编辑")
                }
            },
            dismissButton = {
                TextButton(onClick = { isQuitAlertDialogVisible = false }) {
                    Text(text = "取消")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressSelectBottomSheet(modifier: Modifier = Modifier, onDismissRequest: () -> Unit) {
    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        var s0 by remember {
            mutableStateOf(citiesData.keys.random())
        }
        var s1 by remember {
            mutableStateOf(citiesData[s0]!!.keys.random())
        }
        var s2 by remember {
            mutableStateOf(citiesData[s0]!![s1]!!.random())
        }
        LaunchedEffect(s0) {
            s1 = citiesData[s0]!!.keys.random()
        }
        LaunchedEffect(s1) {
            s2 = citiesData[s0]!![s1]!!.first()
        }
        Row(Modifier.height(240.dp)) {
            LazyColumn(Modifier.weight(1f)) {
                items(
                    items = citiesData.keys.toList(),
                ) {
                    Text(
                        text = it, modifier = Modifier
                            .fillMaxWidth()
                            .clickable { s0 = it }
                            .padding(vertical = 4.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
            LazyColumn(Modifier.weight(1f)) {
                items(
                    items = citiesData[s0]!!.keys.toList(),
                ) {
                    Text(text = it)
                }
            }
            LazyColumn(Modifier.weight(1f)) {
//                items(
//                    items = citiesData[s0].[s1]!!.toList(),
//                ) {
//                    Text(text = it)
//                }
            }
        }
    }
}


private suspend fun checkInformation(
    context: Context,
    name: String,
    phone: String,
    address: String,
    detailAddress: String
) {
    if (name.isEmpty()) {
        Toast.makeText(context, "请输入收货人姓名", Toast.LENGTH_SHORT).show()
        return
    }
    if (phone.isEmpty()) {
        Toast.makeText(context, "请输入收货人手机号", Toast.LENGTH_SHORT).show()
        return
    }
    if (address.isEmpty()) {
        Toast.makeText(context, "请选择收货人地址", Toast.LENGTH_SHORT).show()
        return
    }
    if (detailAddress.isEmpty()) {
        Toast.makeText(context, "请输入收货人详细地址", Toast.LENGTH_SHORT).show()
        return
    }
    SettingStore.AddressList().add(Address(name, phone, address, detailAddress))
}