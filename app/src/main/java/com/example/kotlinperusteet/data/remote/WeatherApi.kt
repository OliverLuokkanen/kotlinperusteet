package com.example.kotlinperusteet.data.remote

import com.example.kotlinperusteet.BuildConfig
import com.example.kotlinperusteet.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String = BuildConfig.WEATHER_API_KEY,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "fi"
    ): WeatherResponse
}