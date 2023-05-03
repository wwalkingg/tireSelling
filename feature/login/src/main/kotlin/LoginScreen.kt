import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(modifier: Modifier = Modifier, component: LoginComponent) {
//    val snackbarHostState = remember { SnackbarHostState() }
//    Scaffold(modifier, snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
//        val loginUIState by component.modelState.loginUIStateFlow.collectAsState()
//        Box(Modifier.padding(padding).fillMaxSize()) {
//            Column(Modifier.align(Alignment.Center)) {
//                Text("Login", style = MaterialTheme.typography.displayMedium)
//                Spacer(Modifier.height(20.dp))
//                TextField(
//                    value = component.modelState.username,
//                    onValueChange = { component.modelState.username = it },
//                    label = { Text("电话") },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
//                    singleLine = true
//                )
//                Spacer(Modifier.height(5.dp))
//                TextField(
//                    value = component.modelState.password,
//                    onValueChange = { component.modelState.password = it },
//                    label = { Text("密码") },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//                    visualTransformation = PasswordVisualTransformation(),
//                    singleLine = true
//                )
//                Spacer(Modifier.height(10.dp))
//                Button(onClick = { component.modelState.login() }) {
//                    Text("登录")
//                }
//                Spacer(Modifier.height(10.dp))
//                TextButton(onClick = { component.modelState.register() }) {
//                    Text("使用此信息注册")
//                }
//            }
//
//            Column(modifier = Modifier.padding(20.dp).fillMaxWidth().align(Alignment.BottomCenter)) {
//                Divider()
//
//            }
//        }
//        when (loginUIState) {
//            LoginUIState.Loading -> {
//                Dialog(onDismissRequest = {}) {
//                    Box(
//                        modifier = Modifier.clip(CircleShape)
//                            .background(MaterialTheme.colorScheme.surface)
//                    ) {
//                        CircularProgressIndicator(Modifier.padding(30.dp).align(Alignment.Center))
//                    }
//                }
//            }
//
//            is LoginUIState.LoginError -> {
//                LaunchedEffect(Unit) {
//                    val msg = (loginUIState as LoginUIState.LoginError).throwable.message ?: "登录失败"
//                    snackbarHostState.showSnackbar(message = msg)
//                }
//            }
//
//            is LoginUIState.LoginLoaded -> {
//                navigation.replaceAll(NavConfig.Home)
//            }
//
//            is LoginUIState.RegisterError -> {
//                LaunchedEffect(Unit) {
//                    val msg = (loginUIState as LoginUIState.RegisterError).throwable.message ?: "登录失败"
//                    snackbarHostState.showSnackbar(message = msg)
//                }
//            }
//
//            LoginUIState.RegisterLoaded -> {
//                LaunchedEffect(Unit) {
//                    snackbarHostState.showSnackbar(message = "注册成功，点击登录登录")
//                }
//            }
//
//            LoginUIState.None -> {}
//        }
//    }
}






