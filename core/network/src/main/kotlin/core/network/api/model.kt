package core.network.api

import com.example.android.core.model.Model
import com.example.android.core.model.ModelDetail
import core.network.Resp
import httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun Apis.Model.getHotModels() = callbackFlow {
    httpClient.get("models/hot").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Model>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

fun Apis.Model.getAllModels() = callbackFlow {
    httpClient.get("models").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Model>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

fun Apis.Model.getModelDetail(id:Int) = callbackFlow {
    httpClient.get("models/${id}").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<ModelDetail>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}