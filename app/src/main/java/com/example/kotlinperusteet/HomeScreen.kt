package com.example.kotlinperusteet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation. layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout. Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation. layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation. layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation. lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx. compose.material3.Text
import androidx.compose.runtime.Composable
import androidx. compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(viewModel: TaskViewModel, modifier: Modifier = Modifier) {
    val tasks = viewModel.tasks.value

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Tasks (${tasks.size})",
            style = MaterialTheme.typography. headlineMedium
        )

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { viewModel.sortByDueDate() }) {
                Text("Sort")
            }
            Button(onClick = { viewModel.filterByDone(false) }) {
                Text("Undone")
            }
            Button(onClick = { viewModel.resetTasks() }) {
                Text("Reset")
            }
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                val nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                val newTask = Task(
                    id = nextId,
                    title = "Uusi tehtävä #$nextId",
                    description = "Lisätty addTask-funktiolla",
                    priority = 2,
                    dueDate = "2026-01-15",
                    done = false
                )
                viewModel.addTask(newTask)
            }
        ) {
            Text("Add Task")
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(tasks) { task ->
                TaskRow(
                    task = task,
                    onToggle = { viewModel.toggleDone(task.id) },
                    onDelete = { viewModel.removeTask(task.id) }
                )
            }
        }
    }
}

@Composable
fun TaskRow(task: Task, onToggle: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            . fillMaxWidth()
            .clickable { onToggle() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = task. done,
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
        Button(onClick = { onDelete() }) {
            Text("Delete")
        }
    }
}