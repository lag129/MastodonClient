package net.lag129.mastodon.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

object PreferencesKeys {
    val SERVER_NAME_KEY = stringPreferencesKey("server_name")
    val BEARER_TOKEN_KEY = stringPreferencesKey("bearer_token")
}

@Singleton
class PreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveServerName(serverName: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SERVER_NAME_KEY] = serverName
        }
    }

    suspend fun readServerName(): String {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.SERVER_NAME_KEY] ?: ""
        }.first()
    }

    suspend fun saveBearerToken(bearerToken: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.BEARER_TOKEN_KEY] = bearerToken
        }
    }

    suspend fun readBearerToken(): String {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.BEARER_TOKEN_KEY] ?: ""
        }.first()
    }
}