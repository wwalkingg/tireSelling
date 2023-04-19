package core.network.api

import com.example.android.core.model.Article
import core.network.Resp
import httpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.isSuccess
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

suspend fun Apis.Article.getHotArticles() = callbackFlow {
    httpClient.get("plants/recommend").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Article>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else {
            this@callbackFlow.cancel(status.description)
        }
        awaitClose { }
    }
}

suspend fun Apis.Article.getArticles() = callbackFlow {
    httpClient.get("plant/findAllPlantTechnology").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Article>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

suspend fun Apis.Article.getArticleDetail(id: Int) = callbackFlow {
    httpClient.get("article/$id").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<Article>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}