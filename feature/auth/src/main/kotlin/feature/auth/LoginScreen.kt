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
import core.common.navigation.rootNavigation
import core.design_system.Icons
import core.design_system.component.loading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(modifier: Modifier = Modifier, component: LoginComponent) {
    val snackbarHostState = remember { SnackbarHostState() }
    val loginUIState by component.modelState.loginUIStateFlow.collectAsState()
    LaunchedEffect(Unit) {
        when (loginUIState) {
            is LoginUIState.Error -> {
                snackbarHostState.showSnackbar("登录失败")
            }

            is LoginUIState.Success -> {
                snackbarHostState.showSnackbar("登录成功")
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
            modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("登录", style = MaterialTheme.typography.titleLarge)
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
            Button(
                onClick = { component.modelState.login() }) {
                Text("登录")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(title = {
        Text("登录")
    }, navigationIcon = {
        IconButton(onClick = { rootNavigation.pop() }) {
            Icon(painterResource(Icons.caretLeft), contentDescription = null)
        }
    })
}