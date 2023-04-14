package core.network

import android.util.Log
import io.ktor.client.call.*
import io.ktor.client.plugins.api.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import io.ktor.util.reflect.*
import io.ktor.utils.io.*
import io.ktor.utils.io.copyTo
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.GlobalScope
import java.io.ByteArrayOutputStream

val MyLoggingPlugin = createClientPlugin("MyLoggingPlugin") {
    onRequest { request, content ->
        val requestLog = """
            ================== Request ==================
            ${request.method.value} ${request.url}
            =Authorization: ${request.headers["Authorization"]}
            =Parameter:
            ${request.url.parameters.entries().joinToString("\n") { "#${it.key}: ${it.value}" }}
            $content
        """.trimIndent()
        Log.i("HttpClient", requestLog)
    }
    onResponse {
        val responseLog = """
            ================== Response ==================
            ${it.status.value} ${it.request.url}
        """.trimIndent()
        Log.i("HttpClient", responseLog)
    }
}