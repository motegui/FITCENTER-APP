package ar.edu.itba.hci.fitcenter.api

import ar.edu.itba.hci.fitcenter.BuildConfig
import ar.edu.itba.hci.fitcenter.api.Client.client
import kotlin.math.pow
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.delete
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders

/**
 * Data Layer -> Data Source
 * Provides methods for accessing the app's API.
 */
object ApiRepository {
    private const val BASE_URL = BuildConfig.API_BASE_URL

    // Check if HTTP status code is 2xx
    private fun ok(response: HttpResponse): Boolean {
        return response.status.value / 10.0.pow((2).toDouble()).toInt() % 10 == 2
    }

    private suspend inline fun <reified T> parse(response: HttpResponse): T {
        if (!ok(response)) {
            val data: Models.Error = response.body()
            var message = data.description
            if (data.details != null) message += " (${data.details.joinToString(", ")})"
            throw Exception(message)
        }
        return response.body()
//        val result: T = response.body()
//        return result
    }

    suspend fun login(credentials: Models.Credentials): Models.AuthenticationToken {
        val response = client.post("${BASE_URL}users/login") {
            setBody(credentials)
        }
        return parse(response)
    }

    suspend fun logout(sessionToken: String) {
        val response = client.post("${BASE_URL}users/logout") {
            header(HttpHeaders.Authorization, "bearer $sessionToken")
        }
        parse<Unit>(response)
    }

    suspend fun fetchCurrentUser(sessionToken: String): Models.FullUser {
        val response = client.get("${BASE_URL}users/current") {
            header(HttpHeaders.Authorization, "bearer $sessionToken")
        }
        return parse(response)
    }

    suspend fun fetchCycles(
        sessionToken: String,
        routineId: Long,
    ): Models.SearchResult<Models.FullCycle> {
        val response = client.get("${BASE_URL}routines/$routineId/cycles") {
            header(HttpHeaders.Authorization, "bearer $sessionToken")
            parameter("size", Int.MAX_VALUE)
        }
        return parse(response)
    }

    suspend fun fetchCycleExercises(
        sessionToken: String,
        cycleId: Long,
    ): Models.SearchResult<Models.FullCycleExercise> {
        val response = client.get("${BASE_URL}cycles/$cycleId/exercises") {
            header(HttpHeaders.Authorization, "bearer $sessionToken")
            parameter("size", Int.MAX_VALUE)
        }
        return parse(response)
    }

    suspend fun addFavorite(sessionToken: String, routineId: Long) {
        val response = client.post("${BASE_URL}favourites/$routineId") {
            header(HttpHeaders.Authorization, "bearer $sessionToken")
        }
        return parse(response)
    }

    suspend fun removeFavorite(sessionToken: String, routineId: Long) {
        val response = client.delete("${BASE_URL}favourites/$routineId") {
            header(HttpHeaders.Authorization, "bearer $sessionToken")
        }
        return parse(response)
    }

    suspend fun fetchRoutines(
        sessionToken: String
    ): Models.SearchResult<Models.FullRoutine> {
        val response = client.get("${BASE_URL}users/current/routines") {
            header(HttpHeaders.Authorization, "bearer $sessionToken")
            parameter("size", Int.MAX_VALUE)
        }
        return parse(response)
    }

    suspend fun fetchPublicRoutines(
        sessionToken: String
    ): Models.SearchResult<Models.FullRoutine> {
        val response = client.get("${BASE_URL}routines") {
            header(HttpHeaders.Authorization, "bearer $sessionToken")
            parameter("size", Int.MAX_VALUE)
        }
        return parse(response)
    }

    suspend fun fetchRoutine(sessionToken: String, routineId: Long): Models.FullRoutine {
        val response = client.get("${BASE_URL}routines/$routineId") {
            header(HttpHeaders.Authorization, "bearer $sessionToken")
        }
        return parse(response)
    }
}
