package feature.auth

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import core.common.baseUrl
import core.network.utils.error
import core.network.utils.success
import core.ui.component.NavigationTopBar
import core.ui.status_page.ErrorPage
import httpClient
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoModifierScreen(modifier: Modifier = Modifier, component: UserInfoModifierComponent) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { NavigationTopBar(title = "修改用户信息") },
        snackbarHost = { SnackbarHost(component.modelState.snackBarState) }) { padding ->
        val loadUserInfoUIState by component.modelState.loadUserInfoUIStateFlow.collectAsState()
        val modifierUserInfoUIState by component.modelState.modifierUserInfoUIStateFlow.collectAsState()
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (loadUserInfoUIState) {
                LoadUserInfoUIState.Error -> {
                    ErrorPage { }
                }

                LoadUserInfoUIState.Loading -> {
                    Dialog(onDismissRequest = {}) {
                        CircularProgressIndicator()
                    }
                }

                is LoadUserInfoUIState.Success -> {
                    val userInfo = (loadUserInfoUIState as LoadUserInfoUIState.Success).data
                    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                        Column(modifier = Modifier.weight(5f).padding(end = 10.dp)) {
                            TextField(value = component.modelState.name ?: "",
                                onValueChange = {
                                    component.modelState.name = it
                                },
                                singleLine = true,
                                label = { Text("输入你的新名字") },
                                placeholder = { Text(userInfo.name ?: "还没设置名字") })
                            Spacer(Modifier.height(10.dp))
                            TextField(value = component.modelState.height,
                                onValueChange = {
                                    component.modelState.height = it
                                },
                                label = { Text("身高") },
                                placeholder = { Text(userInfo.height.toString()) })
                            Spacer(Modifier.height(10.dp))
                            TextField(value = component.modelState.weight,
                                onValueChange = {
                                    component.modelState.weight = it
                                },
                                singleLine = true,
                                label = { Text("体重") },
                                placeholder = { Text(userInfo.weight.toString() ?: "还没设置体重") })
                            Spacer(Modifier.height(10.dp))
                            TextField(value = component.modelState.age,
                                onValueChange = {
                                    component.modelState.age = it
                                },
                                singleLine = true,
                                label = { Text("年龄") },
                                placeholder = { Text(userInfo.age.toString() ?: "还没设置名字") })
                            Spacer(Modifier.height(10.dp))
                            Row {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = component.modelState.sex == "1",
                                        onCheckedChange = {
                                            component.modelState.sex = "1"
                                        })
                                    Text("男")
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = component.modelState.sex == "2",
                                        onCheckedChange = {
                                            component.modelState.sex = "2"
                                        })
                                    Text("女")
                                }
                            }

                        }
                        Spacer(Modifier.height(10.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("可以被搜索到")
                            Switch(checked = component.modelState.isPartner,
                                onCheckedChange = {
                                    component.modelState.isPartner = it
                                })
                        }
                        Spacer(Modifier.height(10.dp))
                        Button(onClick = { component.modelState.modifierUserInfo() }) {
                            Text("确认修改")
                        }
                        if (component.modelState.isLoading) {
                            Dialog(onDismissRequest = {}) {
                                Box(
                                    Modifier.fillMaxWidth(.7f).aspectRatio(1f)
                                        .background(MaterialTheme.colorScheme.surface)
                                ) {
                                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}