package core.datastore

import com.example.android.core.model.Product
import com.russhwolf.settings.set
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val SPTag = "SHOP_CAR"

@Serializable
data class ShopCar(
    val productList: MutableList<Pair<Product,Int>>
) {
    fun store(): Boolean = try {
        val jsonString = Json.encodeToString(this)
        settings[SPTag] = jsonString
        true
    } catch (e: Exception) {
        false
    }

    companion object {
        fun retrieve(): ShopCar {
            val jsonString = settings.getStringOrNull(SPTag) ?: return ShopCar(mutableListOf())
            return Json.decodeFromString<ShopCar>(jsonString)
        }
    }
}

