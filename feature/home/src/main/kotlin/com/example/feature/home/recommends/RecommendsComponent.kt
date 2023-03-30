package com.example.feature.home.recommends

import ModelState
import com.arkivanov.decompose.ComponentContext
import com.example.android.core.model.Product

class RecommendsComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext {
    internal val modelState = RecommendsModelState()
}

internal class RecommendsModelState : ModelState() {
    val hotProduct = listOf(
        Product(1, "大米", "https://unsplash.com/photos/yxPDkU6_jAY", true),
        Product(2, "小麦", "https://unsplash.com/photos/Z4q3nE1A2m4", true),
        Product(3, "玉米", "https://unsplash.com/photos/pQeCzoHxEW8", true),
        Product(4, "绿茶", "https://unsplash.com/photos/w9Xu9pVdHt8", true),
        Product(5, "红茶", "https://unsplash.com/photos/9XoR7fiJPeI", true),
        Product(6, "咖啡", "https://unsplash.com/photos/BXO5oVnGJLY", true),
        Product(7, "黄豆", "https://unsplash.com/photos/IGXZ_RuV7qs", true),
        Product(8, "花生", "https://unsplash.com/photos/RwbGz33Ob9Q", true),
        Product(9, "番茄", "https://unsplash.com/photos/_0W8rpvJad4", true),
        Product(10, "茄子", "https://unsplash.com/photos/nY6YfMzW6N0", true)
    )
}
