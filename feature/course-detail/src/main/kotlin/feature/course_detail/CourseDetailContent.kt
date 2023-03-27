package feature.course_detail

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
import coil.compose.AsyncImage
import core.common.baseUrl
import core.model.Course

@Composable
fun CourseDetailContent(modifier: Modifier = Modifier, course: Course) {
    Column(modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(5.dp)) {
        AsyncImage(model = baseUrl + course.cover, contentDescription = null, modifier = Modifier.fillMaxWidth())
        Text(course.title, style = MaterialTheme.typography.titleLarge)
        Text(
            "${course.consumeEnergy}千卡·${course.consumeTime}分钟·${course.consumeTime}·需要经验",
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = "课程介绍",
            style = MaterialTheme.typography.titleMedium
        )
        Divider()
        Text(
            text = course.courseIntroduce,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
