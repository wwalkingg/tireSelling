package feature.home.statistic

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import core.ui.status_page.ErrorPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticScreen(modifier: Modifier = Modifier, component: StatisticComponent) {
    val loadStatisticUIState by component.modelState.loadStatisticUIStateFlow.collectAsState()
    Scaffold(modifier) { paddingValues ->
        Surface(Modifier.padding(paddingValues)) {
            when (loadStatisticUIState) {
                LoadStatisticUIState.Error -> ErrorPage { component.modelState.loadStatistic() }
                LoadStatisticUIState.Loading -> Box(Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                is LoadStatisticUIState.Success -> {
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val data = (loadStatisticUIState as LoadStatisticUIState.Success).statistic
                        Text("总计使用APP次数:${data.useDay}", style = MaterialTheme.typography.displaySmall)
                        Text("课程统计", style = MaterialTheme.typography.displaySmall)
                        data.courses.forEach {
                            Column {
                                Text(
                                    text = it.key,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                val text =
                                    AnnotatedString("已进行") + AnnotatedString(
                                        text = "${it.value.times}次",
                                        spanStyle = SpanStyle(color = MaterialTheme.colorScheme.primary)
                                    ) + AnnotatedString("消耗能量") + AnnotatedString(
                                        text = "${it.value.consumeEnergy}千焦",
                                        spanStyle = SpanStyle(color = MaterialTheme.colorScheme.primary)
                                    ) + AnnotatedString("消耗时间") + AnnotatedString(
                                        text = "${it.value.consumeTimes}小时",
                                        spanStyle = SpanStyle(color = MaterialTheme.colorScheme.primary)
                                    )
                                Text(text = text)
                            }
                            Divider()
                        }
                        Text("标签统计", style = MaterialTheme.typography.displaySmall)
                        data.tags.forEach {
                            Column {
                                Text(
                                    text = it.key,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                val text =
                                    AnnotatedString("已进行") + AnnotatedString(
                                        text = "${it.value.times}次",
                                        spanStyle = SpanStyle(color = MaterialTheme.colorScheme.primary)
                                    ) + AnnotatedString("消耗时间") + AnnotatedString(
                                        text = "${it.value.consumeTimes}小时",
                                        spanStyle = SpanStyle(color = MaterialTheme.colorScheme.primary)
                                    )
                                Text(text = text)
                            }
                            Divider()
                        }
                    }
                }
            }
        }
    }
}
