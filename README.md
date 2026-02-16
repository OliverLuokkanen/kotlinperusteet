# Week 4 – Navigointi Jetpack Composessa

## Navigointi
Jetpack Compose -navigointi toteutetaan yhdellä aktiviteetilla (Single-Activity).
- **NavController** hallitsee navigointia
- **NavHost** sisältää reitit (composable-reitit)

Sovelluksessa on kolme pääruutua:
- **Home** (tehtävälista)
- **Calendar** (tehtävät ryhmiteltynä päivämäärän mukaan)
- **Settings** (dummy)

Navigointi toteutetaan BottomNavigationilla.

## MVVM + Navigointi
Sama **TaskViewModel** ja sama tila jaetaan kaikille ruuduille.
ViewModel luodaan NavHostin yläpuolella, joten tila säilyy navigoinnista huolimatta.

## CalendarScreen
Kalenterinäkymä näyttää kuukauden ruudukossa:
- viikonpäiväotsikot (Ma–Su)
- edellisen/seuraavan kuukauden päivät harmaana
- valitun päivän tehtävät listattuna
- tehtävien määrä näkyy päivässä

## AlertDialog (add/edit)
Uusi tehtävä lisätään ja tehtävää muokataan **AlertDialogilla**.
- Add → `viewModel.addTask(...)`
- Edit → `viewModel.updateTask(...)`
- Delete → `viewModel.removeTask(...)`
