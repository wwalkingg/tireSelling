package core.network.api

import com.example.android.core.model.Product
import core.network.Resp
import httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.utils.EmptyContent.status
import io.ktor.http.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

object Apis {
    object Article
    object Auth
    object Category
    object Product
    object Store
    object Order
    object Model
    object Brand
    object Coupons
}

