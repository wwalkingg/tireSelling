package core.network.api

import com.example.android.core.model.ProductComment
import com.example.android.core.model.StoreComment
import core.network.Resp
import httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.utils.EmptyContent.status
import io.ktor.http.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

suspend fun Apis.Store.getStoreComments(storeId: Int) = callbackFlow {
    httpClient.post("productComment/findAllProductComments") {
        parameter("storeId", storeId)
    }.apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<StoreComment>>>()
            if (resp.code == 200) {
                send(null)
            } else cancel(resp.msg)
        } else cancel(status.description)
        awaitClose { }
    }
}