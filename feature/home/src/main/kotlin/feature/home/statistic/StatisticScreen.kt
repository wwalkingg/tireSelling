package feature.home.statistic

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot
import core.ui.component.NavigationTopBar
import core.ui.status_page.ErrorPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticScreen(modifier: Modifier = Modifier, component: StatisticComponent) {
    val loadStatisticUIState by component.modelState.loadStatisticUIStateFlow.collectAsState()
    Scaffold(
        topBar = { NavigationTopBar(title = "统计") }
    ) {
        Surface(Modifier.padding(it)) {
            when (loadStatisticUIState) {
                LoadStatisticUIState.Error -> ErrorPage { component.modelState.loadStatistic() }
                LoadStatisticUIState.Loading -> Box(Modifier.fillMaxSize()) { CircularProgressIndicator() }
                is LoadStatisticUIState.Success -> {
                    Column {
                        Text("标签统计", style = MaterialTheme.typography.titleMedium)
                        LineGraph(
                            plot = LinePlot(
                                listOf(
                                    LinePlot.Line(
                                        (loadStatisticUIState as LoadStatisticUIState.Success).statistic.tags.map {
                                            DataPoint(it.value.times.toFloat(), it.value.consumeTimes.toFloat())
                                        },
                                        LinePlot.Connection(color = Color.Red.copy(.4f)),
                                        LinePlot.Intersection(color = Color.Red.copy(.8f)),
                                        LinePlot.Highlight(color = Color.Yellow),
                                    )
                                ),
                                grid = LinePlot.Grid(Color.Red.copy(.2f), steps = 4),
                            ),
                            modifier = Modifier.fillMaxWidth().height(200.dp),
                            onSelection = { xLine, points ->
                                // Do whatever you want here
                            }
                        )
                    }
                }
            }
        }
    }
}
