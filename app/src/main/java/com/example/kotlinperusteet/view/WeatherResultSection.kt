package com.example.kotlinperusteet.view

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.kotlinperusteet.data.model.WeatherResponse

@Composable
fun WeatherResultSection(weather: WeatherResponse) {
    val description = weather.weather.firstOrNull()?.description ?: "-"
    Text(text = "Kaupunki: ${weather.name}", style = MaterialTheme.typography.titleMedium)
    Text(text = "Lämpötila: ${weather.main.temp}°C")
    Text(text = "Sää: $description")
}