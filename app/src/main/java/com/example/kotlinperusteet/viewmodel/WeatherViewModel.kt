package com.example.kotlinperusteet.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinperusteet.data.model.WeatherResponse
import com.example.kotlinperusteet.data.remote.RetrofitInstance
import kotlinx.coroutines.launch

data class WeatherUiState(
    val cityInput: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val weather: WeatherResponse? = null
)

class WeatherViewModel : ViewModel() {

    var uiState by mutableStateOf(WeatherUiState())
        private set

    fun onCityChange(newCity: String) {
        uiState = uiState.copy(cityInput = newCity)
    }

    fun fetchWeather() {
        if (uiState.cityInput.isBlank()) {
            uiState = uiState.copy(errorMessage = "Syötä kaupunki")
            return
        }

        uiState = uiState.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.getWeather(uiState.cityInput.trim())
                uiState = uiState.copy(isLoading = false, weather = result)
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Virhe"
                )
            }
        }
    }
}