package com.example.cricketradio.scorecard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MiniScorecardViewModel @Inject constructor(
    private val repo: CricketRepository
) : ViewModel() {

    private val _miniScorecard = MutableStateFlow<MiniScorecardResponse?>(null)
    val miniScorecard: StateFlow<MiniScorecardResponse?> = _miniScorecard

    fun fetchMiniScorecard() {
        viewModelScope.launch {
            try {
                val response = repo.getMiniScorecard()
                _miniScorecard.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}