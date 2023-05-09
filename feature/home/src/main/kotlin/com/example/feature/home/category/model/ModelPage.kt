package com.example.feature.home.category.model

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.push
import com.example.android.core.model.Model
import core.common.NavConfig
import core.common.navigation
import kotlinx.collections.immutable.PersistentList

@Composable
fun ModelPage(models: PersistentList<Model>) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = models) { model ->
            ModelItem(
                modifier = Modifier.height(120.dp),
                model = model,
                onClick = { navigation.push(NavConfig.ModelDetail(model.id)) })
        }
    }
}

@Composable
fun ModelItem(modifier: Modifier = Modifier, model: Model, onClick: () -> Unit) {
    val mergeModifier = modifier
        .shadow(4.dp, shape = MaterialTheme.shapes.small)
        .clip(MaterialTheme.shapes.small)
        .background(MaterialTheme.colorScheme.primaryContainer)
        .clickable { onClick() }
        .padding(vertical = 10.dp)
    Column(mergeModifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = model.modelName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Text(
            text = model.modelNumber,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = model.compatibleVehicles, style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .shadow(6.dp, shape = MaterialTheme.shapes.small)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(4.dp, 2.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
        }

    }
}

@Preview(device = "spec:width=350dp,height=400dp,dpi=440")
@Composable
fun ModelItemPreview() {
    val model = Model(
        compatibleVehicles = "Ford F-150, Chevrolet Silverado, Dodge Ram",
        id = 123456789,
        modelName = "Super Duty",
        modelNumber = "SD-001"
    )

    ModelItem(model = model) {}
}
