package core.datastore

import com.russhwolf.settings.set
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
private const val SPTag = "token"

@Serializable
data class TokenStore(val token: String?) {

    fun store(): Boolean = try {
        val jsonString = Json.encodeToString(token)
        settings[SPTag] = jsonString
        true
    } catch (e: Exception) {
        false
    }

    companion object {
        fun retrieve(): TokenStore {
            val jsonString = settings.getStringOrNull(SPTag) ?: return TokenStore(null)
            return Json.decodeFromString<TokenStore>(jsonString)
        }
    }
}