package core.network.api

import com.example.android.core.model.UserInfo
import core.network.Resp
import core.network.RespWithoutData
import httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow


fun Apis.Auth.login(username: String, password: String) = callbackFlow {
    httpClient.post("customer/login") {
        contentType(ContentType.Application.Json)
        setBody(mapOf("username" to username, "password" to password))
    }.apply {
        if (status.isSuccess()) {
            val resp = body<Resp<Map<String, String>>>()
            if (resp.code == 200) {
                val token = resp.data.get("token")
                token?.let { send(it) } ?: cancel("token is null")

            } else this@callbackFlow.cancel(status.description)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

fun Apis.Auth.register(username: String, password: String) = callbackFlow {
    httpClient.post("customer/register") {
        contentType(ContentType.Application.Json)
        setBody(mapOf("username" to username, "password" to password))
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

fun Apis.Auth.getUserInfo() = callbackFlow {
    httpClient.get("filter/userInfo").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<UserInfo>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

fun Apis.Auth.modifierUserinfo(userInfo: UserInfo) = callbackFlow {
    httpClient.post("filter/updateCustomer"){
        contentType(ContentType.Application.Json)
        setBody(userInfo)
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