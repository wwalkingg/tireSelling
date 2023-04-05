package core.network.api

import com.example.android.core.model.Product
import core.network.Resp
import httpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.isSuccess
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

suspend fun getHotProducts() = callbackFlow {
    httpClient.get("/products/hot").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Product>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else cancel(resp.msg)
        } else cancel(status.description)
        awaitClose { }
    }
}

suspend fun getProducts(categoryId: Int? = null) = callbackFlow {
    httpClient.get("/products") { parameter("categoryId", categoryId) }.apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Product>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else cancel(resp.msg)
        } else cancel(status.description)
        awaitClose { }
    }
}