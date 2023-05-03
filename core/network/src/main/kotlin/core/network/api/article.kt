package core.network.api

import com.example.android.core.model.Article
import core.network.Resp
import httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow


fun Apis.Article.getAllArticle() = callbackFlow {
    httpClient.get("articles").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Article>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}


fun Apis.Article.getArticleDetail(id: Int) = callbackFlow {
    httpClient.get("articles/${id}").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<Article>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}
