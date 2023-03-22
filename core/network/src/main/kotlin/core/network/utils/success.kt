package core.network.utils

import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

suspend inline fun HttpResponse.success(callback: HttpResponse.() -> Unit): HttpResponse {
    val json by lazy { Json { ignoreUnknownKeys = true } }
    if (status == HttpStatusCode.OK) {
        val respString: String = body()
        println(respString)
        val response: ResponseTestWrapper = json.decodeFromString(respString)
        if (response.code == 200) {
            this.apply { callback() }
        }
        return call.response
    }
    return this
}

suspend inline fun <reified T> HttpResponse.success(callback: HttpResponse.(T) -> Unit): HttpResponse {
    val json by lazy { Json { ignoreUnknownKeys = true } }
    if (status == HttpStatusCode.OK) {
        val respString: String = body()
        println(respString)
        val response: ResponseTestWrapper = json.decodeFromString(respString)
        if (response.code == 200) {
            val real: ResponseWrapper<T> = json.decodeFromString(respString)
            this.apply { callback(real.data) }
        }
        return call.response
    }
    return this
}

suspend inline fun HttpResponse.error(callback: HttpResponse.() -> Unit): HttpResponse {
    if (status != HttpStatusCode.OK) {
        this.apply(callback)
        return this
    } else {
        val response: ResponseTestWrapper = body()
        if (response.code != 200) {
            println("error:${response.code}")
            this.apply(callback)
            return this
        }
    }
    return this
}