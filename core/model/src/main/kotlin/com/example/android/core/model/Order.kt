package com.example.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id:Int,
    val storeId:Int,
    val categoryId:Int,
    val name:String,
    val image:String,
    val price:Float,
    val description:String,
    val createTime:String,
    val isDeleted:Boolean,
    val isHot:Boolean
)
