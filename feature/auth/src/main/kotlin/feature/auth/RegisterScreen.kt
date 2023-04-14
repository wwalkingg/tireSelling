package feature.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import core.common.navigation.Config
import core.common.navigation.rootNavigation
import core.design_system.Icons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(modifier: Modifier = Modifier, component: RegisterComponent) {
    val snackbarHostState = remember { SnackbarHostState() }
    val loginUIState by component.modelState.registerUIStateFlow.collectAsState()
    LaunchedEffect(Unit) {
        when (loginUIState) {
            is RegisterUIState.Error -> {
                snackbarHostState.showSnackbar("登录失败", withDismissAction = true)
            }

            is RegisterUIState.Success -> {
                snackbarHostState.showSnackbar("登录成功", withDismissAction = true)
            }

            else -> {}
        }
    }
    Scaffold(
        topBar = { TopBar() },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
            if (loginUIState == RegisterUIState.Loading) {
                Dialog(onDismissRequest = {}) {
                    CircularProgressIndicator()
                }
            }
        }
    ) {
        Column(
            modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("注册", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(20.dp))
            TextField(
                value = component.modelState.id,
                onValueChange = { component.modelState.id = it },
                label = { Text("账号") },
                placeholder = { Text("请输入你的账号") }
            )
            TextField(
                value = component.modelState.password,
                onValueChange = { component.modelState.password = it },
                label = { Text("密码") },
                placeholder = { Text("请输入你的密码") },
                visualTransformation = PasswordVisualTransformation()
            )
            TextField(
                value = component.modelState.confirmPassword,
                onValueChange = { component.modelState.confirmPassword = it },
                label = { Text("确定密码") },
                placeholder = { Text("请输入你的密码") },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = { component.modelState.register() }) {
                Text("注册")
            }
            TextButton(onClick = { rootNavigation.push(Config.RootConfig.Login) }) {
                Text("已经有账号，去登录")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    TopAppBar(title = {
        Text("注册")
    }, navigationIcon = {
        IconButton(onClick = { rootNavigation.pop() }) {
            Icon(painterResource(Icons.caretLeft), contentDescription = null)
        }
    })
}