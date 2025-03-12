package com.example.cricketradio.di

import com.example.cricketradio.scorecard.MiniScorecardApiService
import com.example.cricketradio.venue.VenueApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    explicitNulls = false
                })
            }
        }
    }

    @Provides
    @Singleton
    fun provideMiniScorecardApiService(client: HttpClient): MiniScorecardApiService {
        return MiniScorecardApiService(client)
    }

    @Provides
    @Singleton
    fun provideVenueApiService(client: HttpClient): VenueApiService {
        return VenueApiService(client)
    }
}
