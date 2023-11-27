package ar.edu.itba.hci.fitcenter.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class StorageRepository private constructor(private val dataStore: DataStore<Preferences>) {
    // Singleton class constructor
    companion object {
        @Volatile
        private var INSTANCE: StorageRepository? = null

        fun getStorageRepository(dataStore: DataStore<Preferences>) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: StorageRepository(dataStore).also { INSTANCE = it }
            }
    }

    suspend fun <T> get(key: Preferences.Key<T>): T =
        dataStore.data
            .catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[key] ?: throw Exception("No such key in storage: $key")
            }.first()

    suspend fun <T> get(key: Preferences.Key<T>, default: T): T =
        dataStore.data
            .catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[key] ?: default
            }.first()

    suspend fun <T> set(key: Preferences.Key<T>, value: T) {
        dataStore.edit { app ->
            app[key] = value
        }
    }
}
