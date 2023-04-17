import core.common.Config
import core.network.RespWithoutData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

val httpClient = HttpClient(CIO) {
    defaultRequest {
        url {
            url(Config.baseUrl)
            path(Config.basePath)
        }
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
//        append("Authorization", token)
    }
}


internal suspend fun HttpResponse.resultSuccess(): Boolean {
    val result = body<RespWithoutData>()
    return result.code == 200
}