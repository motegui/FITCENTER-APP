package ar.edu.itba.hci.fitcenter.api

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import ar.edu.itba.hci.fitcenter.storage.Keys
import ar.edu.itba.hci.fitcenter.storage.StorageRepository


/**
 * Store
 * Data Layer -> Repository
 * Brings together the app's two data sources: the API and the Preferences Store.
 * Provides methods for accessing the API that also save/cache necessary information.
 *
 * Use this to interface with the API. Please avoid using api.Routes outside of this class.
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

    private val storage = StorageRepository.getStorageRepository(dataStore)
    private var user: Models.FullUser? = null


    suspend fun login(credentials: Models.Credentials) {
        val token = ApiRepository.login(credentials).token
        storage.set(Keys.SESSION_TOKEN, token)
    }

    suspend fun isLoggedIn(): Boolean = storage.get(Keys.SESSION_TOKEN, "") != ""

    suspend fun logout() {
        ApiRepository.logout()
        storage.set(Keys.SESSION_TOKEN, "")
    }

    suspend fun currentUser(): Models.FullUser? {
        val token = storage.get(Keys.SESSION_TOKEN) ?: return null
        if (user != null) return user
        return ApiRepository.getCurrentUser(token)
    }
}
