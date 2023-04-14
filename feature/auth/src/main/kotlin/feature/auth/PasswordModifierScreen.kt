package feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import core.ui.component.NavigationTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordModifierScreen(modifier: Modifier = Modifier, component: PasswordModifierComponent) {
    val snackbarHostState = SnackbarHostState()
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = modifier,
        topBar = { NavigationTopBar(title = "修改密码") },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        val modifierPasswordUIState by component.modelState.modifierPasswordUIStateFlow.collectAsState()
        when (modifierPasswordUIState) {
            is ModifierPasswordUIState.Error -> LaunchedEffect(Unit) {
                snackbarHostState.showSnackbar("修改失败", withDismissAction = true)
            }

            ModifierPasswordUIState.Loading -> Dialog(onDismissRequest = {}) { CircularProgressIndicator() }
            ModifierPasswordUIState.Success -> LaunchedEffect(Unit) {
                snackbarHostState.showSnackbar("修改成功", withDismissAction = true)
            }

            ModifierPasswordUIState.Wait -> {}
        }
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = component.modelState.oldPassword,
                onValueChange = { component.modelState.oldPassword = it },
                label = { Text("旧密码") }
            )
            TextField(
                value = component.modelState.newPassword,
                onValueChange = { component.modelState.newPassword = it },
                label = { Text("新密码") }
            )
            TextField(
                value = component.modelState.confirmNewPassword,
                onValueChange = { component.modelState.confirmNewPassword = it },
                label = { Text("确认新密码") }
            )
            Text(
                component.modelState.checkMsg,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error
            )
            Button(onClick = { component.modelState.modifierPassword() }) {
                Text("修改密码")
            }
        }
    }
}