package com.example.kotlinperusteet.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlinperusteet.model.Task
import com.example.kotlinperusteet.viewmodel.TaskViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun HomeScreen(
    vm: TaskViewModel,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val tasks by vm.tasks.collectAsState(initial = emptyList())

    var showAdd by remember { mutableStateOf(false) }
    var editTask by remember { mutableStateOf<Task?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        Text("Tasks (${tasks.size})", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(12.dp))

        Button(onClick = { showAdd = true }) {
            Text("Add")
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(tasks, key = { it.id }) { task ->
                TaskRow(
                    task = task,
                    onToggle = { vm.toggleDone(task.id) },
                    onDelete = { vm.removeTask(task.id) },
                    onOpen = { editTask = task }
                )
            }
        }
    }

    if (showAdd) {
        TaskDialog(
            titleInit = "",
            descriptionInit = "",
            dueDateInit = "2026-01-31",
            onDismiss = { showAdd = false },
            onSave = { title, desc, due ->
                if (title.isNotEmpty()) {
                    val nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                    vm.addTask(
                        Task(
                            id = nextId,
                            title = title,
                            description = if (desc.isEmpty()) "-" else desc,
                            priority = 1,
                            dueDate = if (due.isEmpty()) "2026-01-31" else due,
                            done = false
                        )
                    )
                }
                showAdd = false
            }
        )
    }

    editTask?.let { task ->
        TaskDialog(
            titleInit = task.title,
            descriptionInit = task.description,
            dueDateInit = task.dueDate,
            onDismiss = { editTask = null },
            onSave = { title, desc, due ->
                vm.updateTask(task.copy(title = title, description = desc, dueDate = due))
                editTask = null
            },
            onDelete = {
                vm.removeTask(task.id)
                editTask = null
            }
        )
    }
}

@Composable
private fun TaskRow(
    task: Task,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    onOpen: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpen() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = task.done, onCheckedChange = { onToggle() })
            Column(Modifier.padding(start = 8.dp)) {
                Text(task.title)
                Text(task.dueDate, style = MaterialTheme.typography.bodySmall)
            }
        }
        Button(onClick = onDelete) { Text("Delete") }
    }
}