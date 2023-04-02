package core.network.api

import Resp
import com.example.android.core.model.Product
import httpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.isSuccess
import resultSuccess
import withToken

suspend fun getHotProductSort(size: Int): List<Product>? {
    httpClient.get("/product/hot") {
        withToken()
        parameter("size", size)
    }.apply {
        return if (status.isSuccess() && resultSuccess()) {
            body<Resp<List<Product>>>().data
        } else null
    }
}