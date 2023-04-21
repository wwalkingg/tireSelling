package core.network.api

import com.example.android.core.model.Order
import com.example.android.core.model.OrderParam
import com.example.android.core.model.ProductComment
import core.network.Resp
import core.network.RespWithoutData
import httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.utils.EmptyContent.status
import io.ktor.http.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow


suspend fun Apis.Order.getOrders() = callbackFlow {
    httpClient.get("filter/orders").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Order>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

suspend fun Apis.Order.createOrder(orderParam: OrderParam) = callbackFlow {
    httpClient.post("filter/addOrder") {
        contentType(ContentType.Application.Json)
        setBody(orderParam)
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