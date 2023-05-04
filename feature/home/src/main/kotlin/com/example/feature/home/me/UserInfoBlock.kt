package com.example.feature.home.me

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.android.core.model.User

@Composable
fun UserInfoBlock(modifier: Modifier = Modifier, userInfo: User) {
    Row(
        modifier
            .clip(MaterialTheme.shapes.medium)
            .height(IntrinsicSize.Max)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(10.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max)) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userInfo.name?.first().toString(),
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = FontFamily.Cursive
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier, verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = userInfo.name ?: "未设置名字", style = MaterialTheme.typography.titleMedium)
                Text(text = userInfo.username ?: "", style = MaterialTheme.typography.titleMedium)
                Text(text = userInfo.phoneNumber ?: "未设置电话号码", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}