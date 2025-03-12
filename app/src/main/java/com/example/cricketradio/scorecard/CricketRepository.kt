package com.example.cricketradio.scorecard

import javax.inject.Inject

class CricketRepository @Inject constructor(
    private val apiService: MiniScorecardApiService // Injected by Hilt
) {
    suspend fun getMiniScorecard() = apiService.getMiniScorecard()
}