import core.common.Config.baseUrl
import core.datastore.settings
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

val token = settings.getStringOrNull("token") ?: ""
val httpClient = HttpClient(CIO) {
    defaultRequest {
        url(baseUrl)
    }
    install(ContentNegotiation) {
        json(json = Json {
            ignoreUnknownKeys = true
        })
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 15000
    }
    install(Logging)
}

fun HttpRequestBuilder.withToken() {
    headers {
        append("Authorization", token)
    }
}

@Serializable
data class Resp<T>(
    val code: Int,
    val message: String,
    val data: T
)

@Serializable
private data class RespWithoutData(
    val code: Int,
    val message: String
)

internal suspend fun HttpResponse.resultSuccess(): Boolean {
    val result = body<RespWithoutData>()
    return result.code == 200
}