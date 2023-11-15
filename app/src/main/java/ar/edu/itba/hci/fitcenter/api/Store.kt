package ar.edu.itba.hci.fitcenter.api

import androidx.compose.runtime.rememberCoroutineScope
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * Store
 * Use this to interface with the API. Please avoid using api.Routes directly outside of this class.
 * Objects like the current user can be added as properties on this object to cache them in memory.
 * Objects like the session token can be added to the dataStore to save them to the device's storage.
 */
class Store private constructor(private val dataStore: DataStore<Preferences>) {
    // Singleton class constructor
    companion object {
        @Volatile
        private var INSTANCE: Store? = null

        fun getStore(dataStore: DataStore<Preferences>) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Store(dataStore).also { INSTANCE = it }
            }
    }


    private val apiRoutes = Routes()

    private var user: FullUser? = null

    private val SESSION_TOKEN = stringPreferencesKey("session_token")

    suspend fun login(credentials: Credentials): Unit {
        val tokenObject = apiRoutes.login(credentials)
        dataStore.edit { app ->
            app[SESSION_TOKEN] = tokenObject.token
        }
    }

    suspend fun logout() {
        // POST /users/logout
    }

//    suspend fun currentUser(): FullUser {
//        if (user != null) return user
//        // GET /users/current
//    }
}
