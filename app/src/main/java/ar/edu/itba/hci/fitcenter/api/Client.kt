package ar.edu.itba.hci.fitcenter.api

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi

class CustomHttpLogger(): Logger {
    override fun log(message: String) {
        Log.d("FitcenterHttpClient", message)
    }
}


/**
 * HTTP Client
 * Boilerplate I copied from a tutorial. Does not need to be modified.
 * https://rhythamnegi.com/http-request-with-ktor-client-jetpack-compose-android-project-example
 */
object Client {
    // Configure the HTTP client
    @OptIn(ExperimentalSerializationApi::class)
    val client = HttpClient(Android) {
        // Logging plugin
        install(Logging) {
            logger = CustomHttpLogger()
            level = LogLevel.ALL
        }

        // Timeout plugin
        install(HttpTimeout) {
            requestTimeoutMillis = 15000L
            connectTimeoutMillis = 15000L
            socketTimeoutMillis = 15000L
        }

        // JSON Response properties
        install(ContentNegotiation) {
            json()
        }

        // Default request for POST, PUT, DELETE, etc.
        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            // Add this accept() for accept JSON Body or Raw JSON as Request Body
            accept(ContentType.Application.Json)
        }
    }
}
