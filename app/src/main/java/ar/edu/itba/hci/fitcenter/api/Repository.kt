package ar.edu.itba.hci.fitcenter.api

import ar.edu.itba.hci.fitcenter.api.Client.client
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.delete
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders

const val BASE_URL = "http://192.168.0.115:8080"

/**
 * Data Layer -> Data Source
 * Provides methods for accessing the app's API.
 */
object ApiRepository {
    suspend fun login(credentials: Models.Credentials): Models.AuthenticationToken {
        val response = client.post("$BASE_URL/users/login") {
            setBody(credentials)
        }
        val result = response.body<Models.AuthenticationToken>()
        return result
    }

    suspend fun logout() {
        client.post("$BASE_URL/users/logout")
    }

    suspend fun getCurrentUser(sessionToken: String): Models.FullUser {
        return client.get("$BASE_URL/users/current") {
            header(HttpHeaders.Authorization, sessionToken)
        }.body()
    }
}
