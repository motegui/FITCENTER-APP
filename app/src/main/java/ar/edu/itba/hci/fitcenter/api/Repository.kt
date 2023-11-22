package ar.edu.itba.hci.fitcenter.api

import ar.edu.itba.hci.fitcenter.api.Client.client
import kotlin.math.pow
import io.github.cdimascio.dotenv.dotenv
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
    private val BASE_URL: String
        get() {
            val defaultValue = "http://localhost:8080"
            val dotenv = dotenv {
                directory = "/assets"
                filename = "env"
            }
            return try {
                dotenv["API_URL"] ?: defaultValue
            } catch (error: ExceptionInInitializerError) {
                defaultValue
            }
        }

    private fun digit(a: Int, b: Int): Int {
        return a / 10.0.pow((b - 1).toDouble()).toInt() % 10
    }
    private fun throwForStatus(response: HttpResponse): HttpResponse {
        if (digit(response.status.value, 3) != 2) {
            throw Unauthorized(response.status.description)
        }
        return response
    }

    suspend fun login(credentials: Models.Credentials): Models.AuthenticationToken {
        val response = client.post("$BASE_URL/users/login") {
            setBody(credentials)
        }
        return throwForStatus(response).body()
    }

    suspend fun logout() {
        throwForStatus(client.post("$BASE_URL/users/logout"))
    }

    suspend fun fetchCurrentUser(sessionToken: String): Models.FullUser {
        val response = client.get("$BASE_URL/users/current") {
            header(HttpHeaders.Authorization, sessionToken)
        }
        return throwForStatus(response).body()
    }

    suspend fun fetchCycles(
        sessionToken: String,
        routineId: Long,
    ): Models.SearchResult<Models.FullCycle> {
        val response = client.get("$BASE_URL/routines/$routineId/cycles") {
            header(HttpHeaders.Authorization, sessionToken)
            parameter("size", Int.MAX_VALUE)
            parameter("page", 1)
        }
        return throwForStatus(response).body()
    }

    suspend fun fetchCycleExercises(
        sessionToken: String,
        cycleId: Long,
    ): Models.SearchResult<Models.FullCycleExercise> {
        val response = client.get("$BASE_URL/cycles/$cycleId/exercises") {
            header(HttpHeaders.Authorization, sessionToken)
            parameter("size", Int.MAX_VALUE)
            parameter("page", 1)
        }
        return throwForStatus(response).body()
    }

    suspend fun addFavorite(sessionToken: String, routineId: Long) {
        val response = client.post("$BASE_URL/favourites/$routineId") {
            header(HttpHeaders.Authorization, sessionToken)
        }
        return throwForStatus(response).body()
    }

    suspend fun removeFavorite(sessionToken: String, routineId: Long) {
        val response = client.delete("$BASE_URL/favourites/$routineId") {
            header(HttpHeaders.Authorization, sessionToken)
        }
        return throwForStatus(response).body()
    }

    suspend fun fetchRoutines(
        sessionToken: String
    ): Models.SearchResult<Models.FullRoutine> {
        val response = client.get("$BASE_URL/routines") {
            header(HttpHeaders.Authorization, sessionToken)
            parameter("size", Int.MAX_VALUE)
            parameter("page", 1)
        }
        return throwForStatus(response).body()
    }
}
