# Viikko 5 – Sääsovellus (Retrofit + OpenWeather)

## Retrofit & HTTP
Retrofit hoitaa HTTP‑pyynnöt ja kutsuu OpenWeather‑rajapintaa.

## JSON → dataluokat
Gson muuntaa JSON‑vastauksen Kotlin‑dataluokiksi.

## Coroutines
API‑kutsu tehdään taustasäikeessä, UI päivittyy kun data tulee.

## UI‑tila
ViewModel ylläpitää `WeatherUiState`‑tilaa, Compose reagoi muutoksiin.

## API‑avain
`local.properties → BuildConfig → Retrofit`
yt video:
https://youtu.be/-chBj0LAowc
