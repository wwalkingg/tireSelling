package core.network.api

import com.example.android.core.model.LoginResp
import com.example.android.core.model.User
import com.example.android.core.model.param.LoginParam
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
    httpClient.get("filter/getUserInfo").apply {
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
    httpClient.post("filter/updateUserInfo"){
        contentType(ContentType.Application.Json)
        setBody(user)
    }.apply {
        if (status.isSuccess()) {
            val resp = body<RespWithoutData>()
            if (resp.code == 200) {
                send(null)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

fun Apis.Auth.login(username: String, password: String) = callbackFlow {
    httpClient.post("login") {
        contentType(ContentType.Application.Json)
        setBody(
            LoginParam(username, password)
        )
    }.apply {
        if (status.isSuccess()) {
            val resp = body<Resp<LoginResp>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

fun Apis.Auth.register(username: String, password: String) = callbackFlow {
    httpClient.post("register") {
        contentType(ContentType.Application.Json)
        setBody(
            LoginParam(username, password)
        )
    }.apply {
        if (status.isSuccess()) {
            val resp = body<RespWithoutData>()
            if (resp.code == 200) {
                send(null)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}