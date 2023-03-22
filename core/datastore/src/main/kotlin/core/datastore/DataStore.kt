package core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.settings: DataStore<Preferences> by preferencesDataStore(name = "settings")
val Context.userInfo: DataStore<Preferences> by preferencesDataStore(name = "userInfo")
val Context.token: DataStore<Preferences> by preferencesDataStore(name = "token")