package com.example.kotlinperusteet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    var tasks by remember { mutableStateOf(mockTasks) }
    var showOnlyDone by remember { mutableStateOf(false) }
    var selectedId by remember { mutableStateOf<Int?>(null) }

    // edit-tila
    var isEditing by remember { mutableStateOf(false) }
    var editTitle by remember { mutableStateOf("") }
    var editDescription by remember { mutableStateOf("") }
    var editPriority by remember { mutableStateOf("1") }
    var editDueDate by remember { mutableStateOf("2026-01-30") }
    var editDone by remember { mutableStateOf("false") }

    val selectedTask: Task? = selectedId?.let { id -> tasks.firstOrNull { it.id == id } }
    val visibleTasks = if (showOnlyDone) filterByDone(tasks, true) else tasks

    // Edit-tilassa sallitaan pystyscroll ja imePadding, jotta kentät eivät jää näppäimistön alle
    val scrollState = rememberScrollState()
    val rootModifier =
        if (isEditing) {
            modifier
                .fillMaxSize()
                .imePadding()
                .verticalScroll(scrollState)
                .padding(16.dp)
        } else {
            modifier
                .fillMaxSize()
                .padding(16.dp)
        }

    Column(
        modifier = rootModifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Tehtävät", style = MaterialTheme.typography.headlineMedium)
        Text("Valittu id: ${selectedId ?: "-"}")

        // Napit
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    modifier = Modifier.weight(1f),
                    enabled = !isEditing,
                    onClick = {
                        val nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                        val newTask = Task(
                            id = nextId,
                            title = "Uusi task $nextId",
                            description = "Lisätty napista",
                            priority = 3,
                            dueDate = "2026-01-30",
                            done = false
                        )
                        tasks = addTask(tasks, newTask)
                    }
                ) { Text("Add") }

                Button(
                    modifier = Modifier.weight(1f),
                    enabled = !isEditing,
                    onClick = {
                        val id = selectedId ?: tasks.firstOrNull()?.id ?: return@Button
                        tasks = toggleDone(tasks, id)
                        selectedId = id
                    }
                ) { Text("Toggle") }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    modifier = Modifier.weight(1f),
                    enabled = !isEditing,
                    onClick = { showOnlyDone = !showOnlyDone }
                ) { Text(if (showOnlyDone) "Show all" else "Filter done") }

                Button(
                    modifier = Modifier.weight(1f),
                    enabled = !isEditing,
                    onClick = { tasks = sortByDueDate(tasks) }
                ) { Text("Sort") }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    modifier = Modifier.weight(1f),
                    enabled = !isEditing && selectedId != null,
                    onClick = {
                        val id = selectedId ?: return@Button
                        tasks = deleteTask(tasks, id)
                        selectedId = null
                    }
                ) { Text("Delete") }

                Button(
                    modifier = Modifier.weight(1f),
                    enabled = !isEditing && selectedTask != null,
                    onClick = {
                        val t = selectedTask ?: return@Button
                        editTitle = t.title
                        editDescription = t.description
                        editPriority = t.priority.toString()
                        editDueDate = t.dueDate
                        editDone = t.done.toString() // "true"/"false"
                        isEditing = true
                    }
                ) { Text("Edit") }
            }
        }

        // Edit-näkymä
        if (isEditing) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Muokkaa tehtävää", style = MaterialTheme.typography.titleMedium)

                OutlinedTextField(
                    value = editTitle,
                    onValueChange = { editTitle = it },
                    label = { Text("Title") }
                )

                OutlinedTextField(
                    value = editDescription,
                    onValueChange = { editDescription = it },
                    label = { Text("Description") }
                )

                OutlinedTextField(
                    value = editPriority,
                    onValueChange = { editPriority = it },
                    label = { Text("Priority (numero)") }
                )

                OutlinedTextField(
                    value = editDueDate,
                    onValueChange = { editDueDate = it },
                    label = { Text("Due date (YYYY-MM-DD)") }
                )

                OutlinedTextField(
                    value = editDone,
                    onValueChange = { editDone = it },
                    label = { Text("Done (true/false)") }
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val base = selectedTask ?: return@Button

                            val priorityInt = editPriority.toIntOrNull() ?: base.priority
                            val doneBool = when (editDone.trim().lowercase()) {
                                "true" -> true
                                "false" -> false
                                else -> base.done
                            }

                            val updated = base.copy(
                                title = editTitle,
                                description = editDescription,
                                priority = priorityInt,
                                dueDate = editDueDate,
                                done = doneBool
                            )
                            tasks = updateTask(tasks, updated)
                            isEditing = false
                        }
                    ) { Text("Save") }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { isEditing = false }
                    ) { Text("Cancel") }
                }

                Text(
                    text = "Vinkki: jos näppäimistö peittää kenttiä, vieritä tätä näkymää ylöspäin.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // Lista näytetään vain kun ei editoida (selkein + ei LazyColumn/scroll konfliktia)
        if (!isEditing) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(visibleTasks) { task ->
                    val isSelected = task.id == selectedId
                    Text(
                        modifier = Modifier.clickable { selectedId = task.id },
                        text = (if (isSelected) "► " else "") +
                                "${task.id}. ${task.title} | prio ${task.priority} | due ${task.dueDate} | done=${task.done}"
                    )
                }
            }
        }
    }
}