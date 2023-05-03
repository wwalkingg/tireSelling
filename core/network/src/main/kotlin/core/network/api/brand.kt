package core.network.api

import com.example.android.core.model.Brand
import com.example.android.core.model.BrandDetail
import com.example.android.core.model.Product
import core.network.Resp
import httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun Apis.Brand.getHotBrands() = callbackFlow {
    httpClient.get("brands/hot").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Brand>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

fun Apis.Brand.getAllBrands() = callbackFlow {
    httpClient.get("brands").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Brand>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

fun Apis.Brand.getBrandDetail(id:Int) = callbackFlow {
    httpClient.get("brands/${id}").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<BrandDetail>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}