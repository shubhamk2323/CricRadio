package com.example.cricketradio.venue

import javax.inject.Inject

class VenueRepository @Inject constructor(
    private val apiService: VenueApiService // Injected by Hilt
) {
    suspend fun getVenueInfo() = apiService.getVenueInfo()
}
