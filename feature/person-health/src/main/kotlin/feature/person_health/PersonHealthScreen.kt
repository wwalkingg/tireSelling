package feature.person_health

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.arkivanov.decompose.router.stack.pop
import core.common.navigation.rootNavigation
import core.design_system.Icons
import core.ui.status_page.ErrorPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonHealthScreen(modifier: Modifier = Modifier, component: PersonHealthComponent) {
    val uiState by component.modelState.loadUserInfoUIStateFlow.collectAsState()
    Scaffold(topBar = { TopBar(component.modelState.bmi) }) {
        when (uiState) {
            LoadUserInfoUIState.Error -> {
                ErrorPage { component.modelState.loadUserInfo() }
            }

            LoadUserInfoUIState.Loading -> {
                Dialog(onDismissRequest = {}) {
                    CircularProgressIndicator()
                }
            }

            is LoadUserInfoUIState.Success -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(it).padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Column {
                        Text("年龄", style = MaterialTheme.typography.titleMedium)
                        OutlinedTextField(value = component.modelState.age.toString(), onValueChange = {
                            component.modelState.age = it
                        }, modifier = Modifier.width(140.dp))
                        Text("通过修改个人信息来自动计算年龄", style = MaterialTheme.typography.labelSmall)
                    }
                    Column {
                        Text("身高", style = MaterialTheme.typography.titleMedium)
                        OutlinedTextField(value = component.modelState.height.toString(), onValueChange = {
                            component.modelState.height = it
                        }, modifier = Modifier.width(140.dp), trailingIcon = { Text("CM") })
                        Text("单位cm", style = MaterialTheme.typography.labelSmall)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("体重", style = MaterialTheme.typography.titleMedium)
                            OutlinedTextField(value = component.modelState.weight.toString(), onValueChange = {
                                component.modelState.weight = it
                            }, modifier = Modifier.width(140.dp), trailingIcon = { Text("KG") })
                            Text("单位kg", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                    Button(onClick = { component.modelState.modifierUserInfo() }) {
                        Text("保存")
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(bmi: String) {
    TopAppBar(title = { Text("个人建康") }, navigationIcon = {
        IconButton(onClick = { rootNavigation.pop() }) {
            Icon(painterResource(Icons.caretLeft), contentDescription = null)
        }
    }, actions = {
        Text("BMI", style = MaterialTheme.typography.labelMedium)
        Text(bmi, style = MaterialTheme.typography.displayMedium)
    })
}