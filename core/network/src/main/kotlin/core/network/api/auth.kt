package core.network.api

import com.example.android.core.model.User
import core.network.Resp
import core.network.RespWithoutData
import httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow


fun Apis.Auth.getUserInfo() = callbackFlow {
    httpClient.get("userInfo").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<User>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}


fun Apis.Auth.modifierUserInfo(id: Int, user: User) = callbackFlow {
    httpClient.put("user/${id}").apply {
        if (status.isSuccess()) {
            val resp = body<RespWithoutData>()
            if (resp.code == 200) {
                send(null)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}
