import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.arkivanov.decompose.router.stack.pop
import com.example.core.design_system.Lotties
import core.common.navigation
import core.component_base.LoadUIState

@Composable
fun <T> LoadUIStateScaffold(
    loadUIState: LoadUIState<T>,
    modifier: Modifier = Modifier,
    onReload: (() -> Unit)? = null,
    successContent: @Composable (T) -> Unit
) {
    Box(modifier) {
        when (loadUIState) {
            is LoadUIState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.errorContainer),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(Lotties.error))
                    LottieAnimation(
                        composition,
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(.6f),
                        iterations = LottieConstants.IterateForever
                    )
                    if (onReload != null) {
                        Button(onClick = onReload) {
                            Text("重新加载")
                        }
                    }
                    TextButton(onClick = { navigation.pop() }) {
                        Text("返回")
                    }
                    val error = loadUIState.error
                    var isBugReportDialogVisible by remember { mutableStateOf(false) }
                    IconButton(onClick = { isBugReportDialogVisible = true }) {
                        Icon(Icons.Default.BugReport, contentDescription = null)
                    }
                    if (isBugReportDialogVisible) {
                        AlertDialog(
                            onDismissRequest = { isBugReportDialogVisible = false },
                            title = { Text(error.message ?: "Exception") },
                            text = {
                                Text(
                                    error.stackTraceToString(),
                                    modifier = Modifier.heightIn(max = 400.dp).verticalScroll(rememberScrollState())
                                )
                            },
                            confirmButton = {
                                TextButton(onClick = { isBugReportDialogVisible = false }) {
                                    Text("确定")
                                }
                            }
                        )
                    }
                }
            }

            LoadUIState.Loading -> {
                var isQuitLoadAlertDialogVisible by remember { mutableStateOf(false) }
                Dialog(onDismissRequest = { isQuitLoadAlertDialogVisible = true }) {
                    Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .fillMaxWidth(.6f).aspectRatio(1f)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }
                if (isQuitLoadAlertDialogVisible) {
                    AlertDialog(
                        onDismissRequest = { isQuitLoadAlertDialogVisible = false },
                        title = { Text("取消加载并且返回吗？") },
                        confirmButton = { TextButton(onClick = { navigation.pop() }) { Text("取消加载") } },
                        dismissButton = {
                            TextButton(onClick = {
                                isQuitLoadAlertDialogVisible = false
                            }) { Text("继续") }
                        }
                    )
                }
            }

            is LoadUIState.Loaded -> {
                val data = loadUIState.data
                successContent(data)
            }
        }
    }
}