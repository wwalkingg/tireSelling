package core.datastore

import android.content.Context
import com.example.android.core.model.Address
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val SPTag = "Addresses"

@Serializable
class AddressStore(
    val addresses: List<Address>
) {
    fun Context.store(): Boolean {
        try {
            val jsonString = Json.encodeToString(this)
            getSharedPreferences(SPTag, Context.MODE_PRIVATE)
                .edit()
                .putString(SPTag, jsonString)
                .apply()
            return true
        } catch (e: Exception) {
            return false
        }
    }

    companion object {
        fun Context.retrieve(): AddressStore {
            val jsonString = getSharedPreferences(SPTag, Context.MODE_PRIVATE)
                .getString("addresses", "[]")
            return Json.decodeFromString<AddressStore>(jsonString!!)
        }
    }
}