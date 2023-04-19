package core.network.api

import com.example.android.core.model.Category
import core.network.Resp
import httpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

suspend fun Apis.Category.getAllCategories() = callbackFlow {
    httpClient.get("categories").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Category>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose {  }
    }
}

// todo
suspend fun Apis.Category.getHotCategorise() = getAllCategories()