package com.example.kotlinperusteet.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlinperusteet.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    vm: WeatherViewModel,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val state = vm.uiState

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        Text(text = "Sää", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.cityInput,
            onValueChange = vm::onCityChange,
            label = { Text("Kaupunki") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Button(onClick = { vm.fetchWeather() }) {
            Text("Hae sää")
        }

        Spacer(Modifier.height(16.dp))

        when {
            state.isLoading -> CircularProgressIndicator()
            state.errorMessage != null -> Text(
                text = state.errorMessage,
                color = MaterialTheme.colorScheme.error
            )
            state.weather != null -> WeatherResultSection(weather = state.weather)
        }
    }
}