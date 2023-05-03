package core.network.api

import com.example.android.core.model.Product
import com.example.android.core.model.ProductsDetail
import core.network.Resp
import httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

suspend fun Apis.Product.getSwiper() = callbackFlow {
    httpClient.get("swiper").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Product>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

suspend fun Apis.Product.getAllProducts() = callbackFlow {
    httpClient.get("findAllProduct").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Product>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

suspend fun Apis.Product.getHotProducts() = callbackFlow {
    httpClient.get("products/hot").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Product>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

suspend fun Apis.Product.getProductsByStoreId(storeId: Int) = callbackFlow {
    httpClient.get("findAllByStoreId") { parameter("storeId", storeId) }.apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Product>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

suspend fun Apis.Product.getRecommendProducts() = callbackFlow {
    httpClient.get("productRecommend").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Product>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

suspend fun Apis.Product.getProductDetail(productId: Int) = callbackFlow {
    httpClient.get("products/${productId}").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<ProductsDetail>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

suspend fun Apis.Product.getRank() = callbackFlow {
    httpClient.get("rank").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Product>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}