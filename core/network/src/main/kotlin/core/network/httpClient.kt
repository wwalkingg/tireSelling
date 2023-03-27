import core.common.baseUrl
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

val token = settings.getStringOrNull("token") ?: ""
val httpClient = HttpClient(CIO) {
    defaultRequest {
        url(baseUrl)
        headers {
            append("Authorization", token)
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