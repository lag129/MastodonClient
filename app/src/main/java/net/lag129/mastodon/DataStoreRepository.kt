package net.lag129.mastodon

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_auth")

@Singleton
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getServerName(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[SERVER_NAME_KEY]
        }
    }

    fun getBearerToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[BEARER_TOKEN_KEY]
        }
    }

    suspend fun saveServerName(serverName: String) {
        context.dataStore.edit { settings ->
            settings[SERVER_NAME_KEY] = serverName
        }
    }

    suspend fun saveBearerToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[BEARER_TOKEN_KEY] = token
        }
    }

    companion object {
        val SERVER_NAME_KEY = stringPreferencesKey("server_name")
        val BEARER_TOKEN_KEY = stringPreferencesKey("bearer_token")
    }
}