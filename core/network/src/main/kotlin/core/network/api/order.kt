package core.network.api

import com.example.android.core.model.Order
import com.example.android.core.model.ProductsDetail
import com.example.android.core.model.param.OrderParam
import core.network.Resp
import core.network.RespWithoutData
import httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun Apis.Order.createNewOrder(orderParam: OrderParam) = callbackFlow {
    httpClient.post("filter/orders").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<ProductsDetail>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

fun Apis.Order.getAllOrders() = callbackFlow {
    httpClient.get("filter/orders/me").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Order>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}


fun Apis.Order.deleteOrder(id: Int) = callbackFlow {
    httpClient.delete("filter/orders/me/${id}").apply {
        if (status.isSuccess()) {
            val resp = body<RespWithoutData>()
            if (resp.code == 200) {
                send(null)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}


/*
* {
  "receiverName": "string",
  "receiveraddress": "string",
  "receiverPhone": "string",
  "note": "string"
}*/
fun Apis.Order.modifierOrder(
    id: Int,
    receiverName: String,
    receiveraddress: String,
    receiverPhone: String,
    note: String
) = callbackFlow {
    httpClient.put("filter/orders/${id}"){
        contentType(ContentType.Application.Json)
        setBody(mapOf(
            "receiverName" to receiverName,
            "receiveraddress" to receiveraddress,
            "receiverPhone" to receiverPhone,
            "note" to note
        ))
    }.apply {
        if (status.isSuccess()) {
            val resp = body<RespWithoutData>()
            if (resp.code == 200) {
                send(null)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}


fun Apis.Order.confirmDelivery(id: Int) = callbackFlow {
    httpClient.put("filter/orders/${id}/confirm_delivery").apply {
        if (status.isSuccess()) {
            val resp = body<RespWithoutData>()
            if (resp.code == 200) {
                send(null)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}