package ar.edu.itba.hci.fitcenter.api

import ar.edu.itba.hci.fitcenter.api.Client.client
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import kotlin.math.pow
import io.github.cdimascio.dotenv.dotenv

val dotenv = dotenv {
    directory = "/assets"
    filename = "env"
}
val BASE_URL = dotenv["API_URL"] ?: "http://localhost:8080"

/**
 * Data Layer -> Data Source
 * Provides methods for accessing the app's API.
 */
object ApiRepository {
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

    suspend fun getCurrentUser(sessionToken: String): Models.FullUser {
        val response = client.get("$BASE_URL/users/current") {
            header(HttpHeaders.Authorization, sessionToken)
        }
        return throwForStatus(response).body()
    }
}
