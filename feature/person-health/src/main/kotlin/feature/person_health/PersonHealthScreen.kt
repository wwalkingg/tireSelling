package feature.person_health

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.pop
import core.common.navigation.rootNavigation
import core.design_system.Icons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonHealthScreen(modifier: Modifier = Modifier, component: PersonHealthComponent) {
    Scaffold(topBar = { TopBar(component.modelState.bmi) }) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it).padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column {
                Text("年龄", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(value = component.modelState.age.toString(), onValueChange = {
                    component.modelState.age = it.toIntOrNull() ?: 0
                }, modifier = Modifier.width(140.dp))
                Text("通过修改个人信息来自动计算年龄", style = MaterialTheme.typography.labelSmall)
            }
            Column {
                Text("身高", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(value = component.modelState.height.toString(), onValueChange = {
                    component.modelState.height = it.toFloatOrNull() ?: 0f
                }, modifier = Modifier.width(140.dp), trailingIcon = { Text("CM") })
                Text("单位cm", style = MaterialTheme.typography.labelSmall)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("体重", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(value = component.modelState.weight.toString(), onValueChange = {
                        component.modelState.weight = it.toFloatOrNull() ?: 0f
                    }, modifier = Modifier.width(140.dp), trailingIcon = { Text("KG") })
                    Text("单位kg", style = MaterialTheme.typography.labelSmall)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("预期体重", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(value = component.modelState.expectedWeight.toString(), onValueChange = {
                        component.modelState.expectedWeight = it.toFloatOrNull() ?: 0f
                    }, modifier = Modifier.width(140.dp), trailingIcon = { Text("KG") })
                }
            }
            Button(onClick = { component.modelState.saveData() }) {
                Text("保存")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(bmi: Float) {
    TopAppBar(title = { Text("个人建康") }, navigationIcon = {
        IconButton(onClick = { rootNavigation.pop() }) {
            Icon(painterResource(Icons.caretLeft), contentDescription = null)
        }
    }, actions = {
        Text("BMI", style = MaterialTheme.typography.labelMedium)
        Text(String.format("%.2f", bmi), style = MaterialTheme.typography.titleLarge)
    })
}