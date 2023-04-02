package core.network.api

import com.example.android.core.model.ArticleDetail
import httpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.isSuccess
import resultSuccess
import withToken

suspend fun getArticleDetail(id: Int): ArticleDetail? {
    httpClient.get("/technical-article") {
        withToken()
        parameter("id", id)
    }.apply {
        return if (status.isSuccess() && resultSuccess()) {
            body<ArticleDetail>()
        } else null
    }
}