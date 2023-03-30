package com.example.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class CategorySort(val id: Int, val name: String) {
    companion object {
        val fakeData = listOf(
            CategorySort(1, "水果"),
            CategorySort(2, "蔬菜"),
            CategorySort(3, "谷物"),
            CategorySort(4, "畜牧"),
            CategorySort(5, "水产"),
            CategorySort(6, "禽类"),
            CategorySort(7, "调味品"),
            CategorySort(8, "干货"),
            CategorySort(9, "坚果"),
            CategorySort(10, "食用油"),
            CategorySort(11, "豆类"),
            CategorySort(12, "糖果"),
            CategorySort(13, "肉类"),
            CategorySort(14, "海藻"),
            CategorySort(15, "乳制品"),
            CategorySort(16, "饮料"),
            CategorySort(17, "面类")
        )
    }
}
