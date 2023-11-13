package ar.edu.itba.hci.fitcenter.api

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey

class Store private constructor(private val dataStore: DataStore<Preferences>) {
    companion object {
        @Volatile
        private var INSTANCE: Store? = null

        fun getStore(dataStore: DataStore<Preferences>) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Store(dataStore).also { INSTANCE = it }
            }
    }

    private var user: ApiModels.FullUser? = null
    val SESSION_TOKEN = stringPreferencesKey("session_token")

    suspend fun login() {
        dataStore.edit { app ->
//            app[SESSION_TOKEN] = <POST /users/login>
        }
    }

    suspend fun logout() {
        // POST /users/logout
    }

    suspend fun currentUser(): ApiModels.FullUser {
        if (user != null) return user
        // GET /users/current
    }
}
