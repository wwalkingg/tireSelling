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
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { NavigationTopBar(title = "修改用户信息") },
        snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val loadUserInfoUIState by component.modelState.loadUserInfoUIStateFlow.collectAsState()
        val modifierUserInfoUIState by component.modelState.modifierUserInfoUIStateFlow.collectAsState()
        val imagePickerLaunch =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(), onResult = { uri ->
                if (uri != null) {
                    component.modelState.newAvatarInputStream = context.contentResolver.openInputStream(uri)
                }
            })
        when (modifierUserInfoUIState) {
            ModifierUserInfoUIState.Error -> {
                LaunchedEffect(modifierUserInfoUIState) {
                    snackbarHostState.showSnackbar("修改失败")
                }
            }

            ModifierUserInfoUIState.Success -> {
                LaunchedEffect(modifierUserInfoUIState) {
                    snackbarHostState.showSnackbar("修改成功")
                }
            }

            else -> {}
        }
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
                        Row(
                            modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier.clip(CircleShape)
                                    .weight(5f)
                            ) {
                                AsyncImage(
                                    modifier = Modifier.fillMaxSize().clip(CircleShape).alpha(.8f)
                                        .layout { measurable, constraints ->
                                            val placeable = measurable.measure(constraints)
                                            layout(placeable.width, placeable.width) {
                                                placeable.placeRelative(0, 0)
                                            }
                                        },
                                    model = baseUrl + userInfo.avatar,
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds
                                )
                                if (component.modelState.newAvatarInputStream != null) {
                                    Image(
                                        modifier = Modifier.fillMaxSize()
                                            .background(MaterialTheme.colorScheme.primaryContainer).clip(CircleShape)
                                            .alpha(.8f).zIndex(10f).offset(x = 5.dp).layout { measurable, constraints ->
                                                val placeable = measurable.measure(constraints)
                                                layout(placeable.width, placeable.width) {
                                                    placeable.placeRelative(0, 0)
                                                }
                                            },
                                        painter = BitmapPainter(
                                            BitmapFactory.decodeStream(component.modelState.newAvatarInputStream)
                                                .asImageBitmap()
                                        ),
                                        contentScale = ContentScale.FillBounds,
                                        contentDescription = null
                                    )
                                }
                                TextButton(
                                    onClick = { imagePickerLaunch.launch("image/*") },
                                    modifier = Modifier.align(Alignment.Center).zIndex(11f),
                                    colors = ButtonDefaults.textButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primary.copy(
                                            .5f
                                        )
                                    )
                                ) {
                                    Text("点击上传头像", color = MaterialTheme.colorScheme.onError)
                                }
                            }
                            Column(modifier = Modifier.weight(5f).padding(end = 10.dp)) {
                                TextField(value = component.modelState.newUserInfo?.name ?: "",
                                    onValueChange = {
                                        component.modelState.newUserInfo =
                                            component.modelState.newUserInfo!!.copy(name = it)
                                    },
                                    label = { Text("输入你的新名字") },
                                    placeholder = { Text(userInfo.name) })
                                Spacer(Modifier.height(10.dp))
                                TextField(value = component.modelState.newUserInfo?.age.toString() ?: "",
                                    onValueChange = {
                                        component.modelState.newUserInfo =
                                            component.modelState.newUserInfo?.copy(age = it.toIntOrNull() ?: 0)
                                    },
                                    label = { Text("年龄") },
                                    placeholder = { Text(userInfo.age.toString()) })
                            }
                        }
                        Spacer(Modifier.height(10.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("可以被搜索到")
                            Switch(checked = component.modelState.newUserInfo?.isPartner ?: false,
                                onCheckedChange = {
                                    component.modelState.newUserInfo =
                                        component.modelState.newUserInfo!!.copy(isPartner = it)
                                })
                        }
                        Spacer(Modifier.height(10.dp))
                        Button(onClick = { component.modelState.update() }) {
                            if (modifierUserInfoUIState == ModifierUserInfoUIState.Loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(22.dp),
                                    trackColor = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                            Text("确认修改")
                        }
                    }
                }
            }
        }
    }
}