package feature.course_all

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.arkivanov.decompose.router.stack.pop
import core.common.baseUrl
import core.common.navigation.rootNavigation
import core.design_system.Icons
import core.design_system.component.loading
import core.ui.status_page.ErrorPage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CoachAllScreen(modifier: Modifier = Modifier, component: CoachAllComponent) {
    val loadAllCourseUIState by component.modelState.loadAllCoachUIState.collectAsState()
    Scaffold(modifier, topBar = { TopBar() }) { paddingValues
        ->
        when (loadAllCourseUIState) {
            LoadAllCoachUIState.Error -> {
                ErrorPage { component.modelState.loadAllCoach() }
            }

            LoadAllCoachUIState.Loading -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .loading()
                )
            }

            is LoadAllCoachUIState.Success -> {
                val list = (loadAllCourseUIState as LoadAllCoachUIState.Success).data
                Column(
                    Modifier
                        .padding(paddingValues)
                        .padding(horizontal = 10.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    list.forEach {
                        var isDetailDialogVisible by remember {
                            mutableStateOf(false)
                        }
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isDetailDialogVisible = true }
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .height(IntrinsicSize.Max)) {
                            AsyncImage(
                                model = baseUrl + it.avatar,
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.small)
                                    .size(70.dp),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(text = it.name, style = MaterialTheme.typography.titleLarge)
                                Text(
                                    text = it.introduce,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                        Divider()
                        if (isDetailDialogVisible) {
                            Dialog(onDismissRequest = { isDetailDialogVisible = false }) {
                                Column(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.medium)
                                        .background(MaterialTheme.colorScheme.surface)
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        text = it.name,
                                        style = MaterialTheme.typography.displayMedium
                                    )
                                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                        val infos = listOf<Pair<String, String>>(
                                            "工作时间" to it.workYear.toString(),
                                            "性别" to if (it.sex == 1) "男" else "女",
                                            "年龄" to it.age.toString(),
                                            "电话" to it.telephone,
                                            "介绍" to it.introduce
                                        )
                                        infos.forEach {
                                            Row(
                                                modifier = Modifier.height(IntrinsicSize.Max),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = it.first,
                                                    style = MaterialTheme.typography.titleLarge,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                                Spacer(
                                                    modifier = Modifier
                                                        .width(10.dp)
                                                )
                                                Text(
                                                    text = it.second,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                            Divider()
                                        }
                                    }
                                    val context = LocalContext.current
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        modifier = Modifier.align(Alignment.End)
                                    ) {
                                        Button(onClick = {
                                            makePhoneCall(context, it.telephone)
                                        }) {
                                            Text("打电话")
                                        }
                                        Button(onClick = { isDetailDialogVisible = false }) {
                                            Text("关闭")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    TopAppBar(
        title = { Text("教练") },
        navigationIcon = {
            IconButton(onClick = { rootNavigation.pop() }) {
                Icon(painterResource(Icons.caretLeft), contentDescription = null)
            }
        })
}

private const val REQUEST_PHONE_CALL_PERMISSION = 1

fun makePhoneCall(context: Context, phoneNumber: String) {

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
        context.startActivity(intent)
    } else {
        ActivityCompat.requestPermissions(
            context as Activity, arrayOf(Manifest.permission.CALL_PHONE),
            REQUEST_PHONE_CALL_PERMISSION
        )
    }
}