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
class Store private constructor(dataStore: DataStore<Preferences>) {
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
        try {
            ApiRepository.logout(storage.get(Keys.SESSION_TOKEN))
        } catch (_: Exception) {}
        storage.set(Keys.SESSION_TOKEN, "")
    }

    suspend fun currentUser(): Models.FullUser {
        if (user != null) return user as Models.FullUser
        user = ApiRepository.fetchCurrentUser(storage.get(Keys.SESSION_TOKEN))
        return user as Models.FullUser
    }

    private suspend fun <T> collectSearchResult(
        searchFunction: suspend () -> Models.SearchResult<T>
    ): List<T> {
        val results = mutableListOf<T>()
        do {
            val page = searchFunction()
            results.addAll(page.content)
        } while (!page.isLastPage)
        return results
    }

    suspend fun fetchCycles(routineId: Long): List<Models.FullCycle> {
        val token = storage.get(Keys.SESSION_TOKEN)
        return collectSearchResult {
            ApiRepository.fetchCycles(token, routineId)
        }.sortedBy { it.order }
    }

    suspend fun fetchCycleExercises(cycleId: Long): List<Models.FullCycleExercise> {
        val token = storage.get(Keys.SESSION_TOKEN)
        return collectSearchResult {
            ApiRepository.fetchCycleExercises(token, cycleId)
        }.sortedBy { it.order }
    }

    suspend fun fetchRoutines(): List<Models.FullRoutine> =
        collectSearchResult {
            ApiRepository.fetchRoutines(storage.get(Keys.SESSION_TOKEN))
        }

    suspend fun setFavorite(routineId: Long, isFavorite: Boolean) {
        if (isFavorite) {
            ApiRepository.addFavorite(storage.get(Keys.SESSION_TOKEN), routineId)
        } else {
            ApiRepository.removeFavorite(storage.get(Keys.SESSION_TOKEN), routineId)
        }
    }
}
