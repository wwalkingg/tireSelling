package feature.search

import FilterSearchTypes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.push
import core.common.navigation.Config
import core.common.navigation.rootNavigation
import core.model.SearchResult
import core.ui.component.CoachCard
import core.ui.component.CourseCard
import kotlinx.collections.immutable.PersistentSet

@Composable
fun SearchResultContent(
    modifier: Modifier = Modifier,
    filterSearchTypes: PersistentSet<FilterSearchTypes>,
    searchResult: SearchResult
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (filterSearchTypes.contains(FilterSearchTypes.Course)) {
            Text("课程", style = MaterialTheme.typography.titleMedium)
            Divider()
            searchResult.courses.forEach { course ->
                CourseCard(
                    modifier = Modifier.fillMaxWidth(),
                    course = course,
                    onClick = { rootNavigation.push(Config.RootConfig.CourseDetail(it)) })
            }
        }
        if (filterSearchTypes.contains(FilterSearchTypes.Coach)) {
            Text("教练", style = MaterialTheme.typography.titleMedium)
            Divider()
            searchResult.coaches.forEach { coach ->
                CoachCard(
                    modifier = Modifier.fillMaxWidth(),
                    coach,
                    onClick = { rootNavigation.push(Config.RootConfig.CoachDetail(coach.id)) })
            }
        }

        if (filterSearchTypes.contains(FilterSearchTypes.Partner)) {
            Text("伙伴", style = MaterialTheme.typography.titleMedium)
            Divider()
            searchResult.partners.forEach { course ->

            }
        }

        if (filterSearchTypes.contains(FilterSearchTypes.Record)) {
            Text("记录", style = MaterialTheme.typography.titleMedium)
            Divider()
            searchResult.records.forEach { course ->

            }
        }

    }
}