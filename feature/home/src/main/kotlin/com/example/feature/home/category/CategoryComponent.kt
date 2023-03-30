package com.example.feature.home.category

import ModelState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.CategorySort
import com.example.android.core.model.Product

class CategoryComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    internal val modelState = CategoryModelState()
}

internal class CategoryModelState : ModelState() {
    val categorySorts = CategorySort.fakeData
    var selectedCategorySort by mutableStateOf(1)

    private val categorySortAndProducts = mutableMapOf<Int, List<Product>>(
        1 to Product.fakeData
    )

    val shownProducts get() = categorySortAndProducts[selectedCategorySort] ?: listOf()
}
