package com.example.kotlinperusteet.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kotlinperusteet.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun CalendarScreen(
    vm: TaskViewModel,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val tasks by vm.tasks.collectAsState(initial = emptyList())
    val sdf = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    // Kuukausi ja valittu päivä
    var currentMonth by remember { mutableStateOf(Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, 1)
    }) }
    var selectedDate by remember { mutableStateOf(sdf.format(Calendar.getInstance().time)) }

    // Päivien listaus 6 viikkoa (42 solua)
    val days = remember(currentMonth) { buildMonthGrid(currentMonth) }

    // Tehtävät päivämäärän mukaan
    val tasksByDate = tasks.groupBy { it.dueDate }
    val selectedTasks = tasksByDate[selectedDate].orEmpty()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        // Kuukausiotsikko + edellinen/seuraava
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = {
                currentMonth = (currentMonth.clone() as Calendar).apply { add(Calendar.MONTH, -1) }
            }) { Text("<") }

            Text(
                text = monthTitle(currentMonth),
                style = MaterialTheme.typography.headlineMedium
            )

            TextButton(onClick = {
                currentMonth = (currentMonth.clone() as Calendar).apply { add(Calendar.MONTH, 1) }
            }) { Text(">") }
        }

        Spacer(Modifier.height(8.dp))

        // Viikonpäiväotsikot
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("Ma", "Ti", "Ke", "To", "Pe", "La", "Su").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        // Kalenteriruudukko
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(days) { cell ->
                val dayText = cell.day.toString()
                val isSelected = cell.dateString == selectedDate

                Surface(
                    tonalElevation = 1.dp,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clickable { selectedDate = cell.dateString }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(if (isSelected) Color(0xFFBBDEFB) else Color.Transparent)
                            .padding(6.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Column {
                            Text(
                                text = dayText,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (cell.inCurrentMonth) Color.Unspecified else Color.Gray
                            )
                            val count = tasksByDate[cell.dateString]?.size ?: 0
                            if (count > 0) {
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "• $count",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF1976D2)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Valitun päivän tehtävät
        Text(
            text = "Tehtävät ($selectedDate)",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))

        if (selectedTasks.isEmpty()) {
            Text("Ei tehtäviä", color = Color.Gray)
        } else {
            selectedTasks.forEach { task ->
                Text("• ${task.title}")
            }
        }
    }
}

private data class DayCell(
    val day: Int,
    val dateString: String,
    val inCurrentMonth: Boolean
)

private fun buildMonthGrid(monthStart: Calendar): List<DayCell> {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val cal = (monthStart.clone() as Calendar).apply {
        set(Calendar.DAY_OF_MONTH, 1)
    }

    // Ma–Su: siirrä takaisin maanantaihin
    val firstDayOfWeek = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7 // ma=0..su=6
    cal.add(Calendar.DAY_OF_MONTH, -firstDayOfWeek)

    val cells = mutableListOf<DayCell>()
    repeat(42) {
        val inCurrentMonth = cal.get(Calendar.MONTH) == monthStart.get(Calendar.MONTH)
        cells.add(
            DayCell(
                day = cal.get(Calendar.DAY_OF_MONTH),
                dateString = sdf.format(cal.time),
                inCurrentMonth = inCurrentMonth
            )
        )
        cal.add(Calendar.DAY_OF_MONTH, 1)
    }
    return cells
}

private fun monthTitle(cal: Calendar): String {
    val monthNames = listOf(
        "Tammikuu", "Helmikuu", "Maaliskuu", "Huhtikuu", "Toukokuu", "Kesäkuu",
        "Heinäkuu", "Elokuu", "Syyskuu", "Lokakuu", "Marraskuu", "Joulukuu"
    )
    return "${monthNames[cal.get(Calendar.MONTH)]} ${cal.get(Calendar.YEAR)}"
}