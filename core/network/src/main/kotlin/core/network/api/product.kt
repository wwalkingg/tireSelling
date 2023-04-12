package core.network.api

import com.example.android.core.model.Product
import core.network.Resp
import httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

suspend fun Apis.Product.getHotProducts() = callbackFlow {
    httpClient.get("products/hot").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Product>>>()
            println("++ ${resp}")
            if (resp.code == 200) {
                send(resp.data)
            } else cancel(resp.msg)
        } else cancel(status.description)
        awaitClose { }
    }
}

suspend fun Apis.Product.getProducts(categoryId: Int? = null) = callbackFlow {
    httpClient.get("products") { parameter("categoryId", categoryId) }.apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Product>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else cancel(resp.msg)
        } else cancel(status.description)
        awaitClose { }
    }
}