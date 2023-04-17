// To parse the JSON, install Klaxon and do:
//
//   val 商品评论 = 商品评论.fromJson(jsonString)

package com.example.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class StoreComment(
    val avatar: String,
    val content: String,
    val createTime: String,
    val customerId: Int,
    val id: Int,
    val name: String,
    val storeId: Long
)
