
# Week2 – Kotlinperusteet

## Compose-tilanhallinta (state)
Jetpack Composessa UI piirtyy tilan (state) perusteella. Kun tila muuttuu, Compose tekee automaattisesti uuden piirtokierroksen (recomposition) ja näkymä päivittyy.

Tässä työssä tehtävälista on `mutableStateOf(List<Task>)` -tilassa ViewModelissä. Kun listaa muutetaan (esim. `toggleDone`, `removeTask` tai `addTask`), `tasks.value` saa uuden listan ja UI päivittyy automaattisesti.

## Miksi ViewModel on parempi kuin pelkkä remember?
`remember` säilyttää tilan vain niin kauan kuin Composable pysyy muistissa. Kun Activity luodaan uudelleen (esim. näytön kääntö), `remember`-tila voi hävitä.

ViewModel:
- säilyy konfiguraatiomuutoksissa (kuten näytön käännössä)
- pitää tilan erillään UI:sta (selkeämpi rakenne)
- helpottaa logiikan testaamista ja ylläpitoa

Tässä työssä `HomeScreen` hakee ViewModelin `viewModel()`-funktiolla, ja UI päivittyy automaattisesti, kun ViewModelin `tasks`-tila muuttuu.

Linkki viikon 2 youtube videoon https://youtube.com/shorts/MfWBqg5HV70?si=tjSwoT0Fqs4_Dx9X
