package com.example.cricketradio.venue

import VenueInfoResponse
import android.util.Log
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class VenueApiService @Inject constructor(
    private val client: HttpClient
) {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }

    suspend fun getVenueInfo(): VenueInfoResponse? {
        return try {
            val response: HttpResponse = client.get {
                url("http://3.6.243.12:5001/api/v2/match/venue-info")
                headers {
                    append(HttpHeaders.Authorization,
                        "Basic Y3JpY2tldFJhZGlvOmNyaWNrZXRAJCUjUmFkaW8xMjM=")
                    append(HttpHeaders.Accept,
                        "application/json")
                }
                parameter("key", "SA_vs_SL_2024-12-05_1732276435.300452")
            }

            val jsonString = response.bodyAsText()
            Log.d("API_RESPONSE", jsonString)
            json.decodeFromString<VenueInfoResponse>(jsonString)

        } catch (e: Exception) {
            Log.e("API_TEST", "API Call Failed: ${e.message}", e)
            null
        }
    }
}
