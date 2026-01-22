package com.example.kotlinperusteet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    vm: TaskViewModel = viewModel()
) {
    val tasks = vm.tasks.value

    val titleState = remember { mutableStateOf("") }
    val descState = remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Tasks (${tasks.size})",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { vm.sortByDueDate() }) { Text("Sort") }
            Button(onClick = { vm.filterByDone(false) }) { Text("Undone") }
            Button(onClick = { vm.resetTasks() }) { Text("Reset") }
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = titleState.value,
            onValueChange = { titleState.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Title") },
            singleLine = true
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = descState.value,
            onValueChange = { descState.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Description") }
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                val title = titleState.value.trim()
                val desc = descState.value.trim()

                if (title.isNotEmpty()) {
                    val nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                    vm.addTask(
                        Task(
                            id = nextId,
                            title = title,
                            description = if (desc.isEmpty()) "-" else desc,
                            priority = 1,
                            dueDate = "2026-01-22",
                            done = false
                        )
                    )
                    titleState.value = ""
                    descState.value = ""
                }
            }
        ) {
            Text("Add")
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(tasks) { task ->
                TaskRow(
                    task = task,
                    onToggle = { vm.toggleDone(task.id) },
                    onDelete = { vm.removeTask(task.id) }
                )
            }
        }
    }
}

@Composable
fun TaskRow(task: Task, onToggle: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = task.done,
                onCheckedChange = { onToggle() }
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(text = task.title)
                Text(
                    text = "${task.description} • ${task.dueDate} • p${task.priority}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Button(onClick = onDelete) {
            Text("Delete")
        }
    }
}