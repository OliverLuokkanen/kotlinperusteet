package com.example.kotlinperusteet.view

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlinperusteet.model.Task
import com.example.kotlinperusteet.viewmodel.TaskViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    vm: TaskViewModel = viewModel()
) {
    val tasks by vm.tasks.collectAsState(initial = emptyList())

    var selectedTask by remember { mutableStateOf<Task?>(null) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text("Tasks (${tasks.size})", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Title") },
            singleLine = true
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Description") }
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                val t = title.trim()
                val d = description.trim()
                if (t.isNotEmpty()) {
                    val nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                    vm.addTask(
                        Task(
                            id = nextId,
                            title = t,
                            description = if (d.isEmpty()) "-" else d,
                            priority = 1,
                            dueDate = "2026-01-31",
                            done = false
                        )
                    )
                    title = ""
                    description = ""
                }
            }
        ) { Text("Add") }

        Spacer(Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(tasks, key = { it.id }) { task ->
                TaskRow(
                    task = task,
                    onToggle = { vm.toggleDone(task.id) },
                    onDelete = { vm.removeTask(task.id) },
                    onOpen = { selectedTask = task }
                )
            }
        }
    }

    selectedTask?.let { task ->
        DetailScreen(
            task = task,
            onDismiss = { selectedTask = null },
            onUpdate = { vm.updateTask(it) },
            onDelete = {
                vm.removeTask(task.id)
                selectedTask = null
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