package core.network.api

import com.example.android.core.model.Product
import com.example.android.core.model.Store
import com.example.android.core.model.StoreActivity
import com.example.android.core.model.StoreComment
import core.network.Resp
import httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow


suspend fun Apis.Store.getStore(storeId: Int) = callbackFlow {
    httpClient.get("store/$storeId") {
        parameter("storeId", storeId)
    }.apply {
        if (status.isSuccess()) {
            val resp = body<Resp<Store>>()
            println("++ $resp")
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

suspend fun Apis.Store.getStoreComments(storeId: Int) = callbackFlow {
    httpClient.get("storeComment/findAllProductComments") {
        parameter("storeId", storeId)
    }.apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<StoreComment>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

suspend fun Apis.Store.getStoreProducts(storeId: Int) = callbackFlow {
    httpClient.get("product/findAllProductByStoreId") {
        parameter("storeId", storeId)
    }.apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Product>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

suspend fun Apis.Store.getStoreActivities(storeId: Int) = callbackFlow {
    httpClient.get("activity/findAllActivityByStoreId") {
        parameter("storeId", storeId)
    }.apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<StoreActivity>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}