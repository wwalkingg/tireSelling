package feature.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.arkivanov.decompose.router.stack.push
import core.common.navigation.Config
import core.common.navigation.rootNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(modifier: Modifier = Modifier, component: LoginComponent) {
    val snackbarHostState = remember { SnackbarHostState() }
    val loginUIState by component.modelState.loginUIStateFlow.collectAsState()
    LaunchedEffect(Unit) {
        when (loginUIState) {
            is LoginUIState.Error -> {
                snackbarHostState.showSnackbar("登录失败", withDismissAction = true)
            }

            is LoginUIState.Success -> {
                snackbarHostState.showSnackbar("登录成功", withDismissAction = true)
            }

            else -> {}
        }
    }
    Scaffold(
        topBar = { TopBar() },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
            if (loginUIState == LoginUIState.Loading) {
                Dialog(onDismissRequest = {}) {
                    CircularProgressIndicator()
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it).fillMaxSize().padding(bottom = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = component.modelState.id,
                onValueChange = { component.modelState.id = it },
                label = { Text("账号") },
                placeholder = { Text("请输入你的账号") }
            )
            OutlinedTextField(
                value = component.modelState.password,
                onValueChange = { component.modelState.password = it },
                label = { Text("密码") },
                placeholder = { Text("请输入你的密码") },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = { component.modelState.login() }) {
                Text("登录")
            }
            TextButton(onClick = { rootNavigation.push(Config.RootConfig.Register) }) {
                Text("还没有账号，去注册")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    TopAppBar(title = {
        Text("登录")
    })
}