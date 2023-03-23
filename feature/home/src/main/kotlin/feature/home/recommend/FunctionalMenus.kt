package feature.home.recommend

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.design_system.Icons

@Composable
internal fun FunctionalMenus(modifier: Modifier = Modifier, onClick: (FunctionalMenu) -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FunctionalMenu.values().forEach {
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .clickable { onClick(it) }
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimaryContainer) {
                    Icon(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(10.dp),
                        painter = painterResource(it.icon),
                        contentDescription = null,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        it.title,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

internal enum class FunctionalMenu(
    val title: String,
    @DrawableRes val icon: Int,
) {
    AllCourse("全部课程", Icons.graduationCapDuotone),
    PersonalHealth("个人健康", Icons.babyDuotone),
    Coach("教练", Icons.chalkboardTeacherDuotone),
    FindPartner("寻找伙伴", Icons.globeHemisphereEastDuotone);
}