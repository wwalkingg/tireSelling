package feature.modifier_userinfo

import LoadUIStateScaffold
import NavigationTopBar
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import core.component_base.PostUIState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ModifierUserinfoScreen(modifier: Modifier = Modifier, component: ModifierUserinfoComponent) {
    val loadUserInfoUIState by component.modelState.loadUserInfoUIStateFlow.collectAsState()
    LoadUIStateScaffold(loadUserInfoUIState) {
        Scaffold(modifier = modifier, topBar = { NavigationTopBar(title = "个人资料") }) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 10.dp)
            ) {
                UserInfoItem(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Username",
                    readOnly = true,
                    value = component.modelState.newUserinfo?.username ?: "未设置"
                ) {}
                Text(text = "username不可更改",style = MaterialTheme.typography.labelSmall,color = Color.Gray)
                UserInfoItem(
                    modifier = Modifier.fillMaxWidth(),
                    label = "用户昵称",
                    value = component.modelState.newUserinfo?.name ?: "未设置"
                ) {
                    component.modelState.newUserinfo = component.modelState.newUserinfo?.copy(username = it)
                }
                UserInfoItem(
                    modifier = Modifier.fillMaxWidth(),
                    label = "电话号码",
                    value = component.modelState.newUserinfo?.phoneNumber ?: "未设置"
                ) {
                    component.modelState.newUserinfo = component.modelState.newUserinfo?.copy(phoneNumber = it)
                }
                UserInfoItem(
                    modifier = Modifier.fillMaxWidth(),
                    label = "用户邮箱",
                    value = component.modelState.newUserinfo?.email ?: "未设置"
                ) {
                    component.modelState.newUserinfo = component.modelState.newUserinfo?.copy(email = it)
                }
                Spacer(Modifier.height(30.dp))
                Button(onClick = { component.modelState.modifier() }) {
                    Text("保存修改")
                }
                val modifierResultUIState by component.modelState.modifierResultUIStateFlow.collectAsState()
                val context = LocalContext.current
                when (modifierResultUIState) {
                    is PostUIState.Error -> {
                        Toast.makeText(context, "失败", Toast.LENGTH_SHORT).show()
                    }

                    PostUIState.Loading -> {
                        Dialog(onDismissRequest = {}) {
                            Box(
                                Modifier
                                    .fillMaxWidth(.8f)
                                    .aspectRatio(1f)
                                    .background(MaterialTheme.colorScheme.surface)
                            ) {
                                CircularProgressIndicator(Modifier.align(Alignment.Center))
                            }
                        }
                    }

                    PostUIState.None -> {}
                    PostUIState.Success -> {
                        Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}


@Composable
fun UserInfoItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    readOnly: Boolean = false,
    onValueChange: (String) -> Unit
) {
    Column(modifier) {
        Row(modifier = Modifier.padding(vertical = 10.dp)) {
            Column {
                Text(text = label, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(5.dp))
                BasicTextField(
                    value,
                    onValueChange,
                    readOnly = readOnly,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Divider()
    }
}



