package com.example.cricketradio.venue

import VenueInfoResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VenueInfoViewModel @Inject constructor(
    private val repo: VenueRepository
) : ViewModel() {

    private val _venueInfo = MutableStateFlow<VenueInfoResponse?>(null)
    val venueInfo: StateFlow<VenueInfoResponse?> = _venueInfo

    fun fetchVenueInfo() {
        viewModelScope.launch {
            try {
                val response = repo.getVenueInfo()
                _venueInfo.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
