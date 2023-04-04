package com.example.feature.home.category

import core.component_base.ModelState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Category
import com.example.android.core.model.Product

class CategoryComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = CategoryModelState()
}

internal class CategoryModelState : ModelState() {
    val categorySorts = Category
    var selectedCategorySort by mutableStateOf(1)

    private val categorySortAndProducts = emptyList<Product>()

    val shownProducts get() = categorySortAndProducts[selectedCategorySort]
}
