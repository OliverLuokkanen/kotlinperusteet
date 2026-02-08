package com.example.kotlinperusteet.view

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*

@Composable
fun TaskDialog(
    titleInit: String,
    descriptionInit: String,
    dueDateInit: String,
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit,
    onDelete: (() -> Unit)? = null
) {
    var title by remember { mutableStateOf(titleInit) }
    var description by remember { mutableStateOf(descriptionInit) }
    var dueDate by remember { mutableStateOf(dueDateInit) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Task") },
        text = {
            androidx.compose.foundation.layout.Column {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
                OutlinedTextField(value = dueDate, onValueChange = { dueDate = it }, label = { Text("Due date (YYYY-MM-DD)") })
            }
        },
        confirmButton = {
            Button(onClick = { onSave(title.trim(), description.trim(), dueDate.trim()) }) {
                Text("Tallenna")
            }
        },
        dismissButton = {
            androidx.compose.foundation.layout.Row {
                if (onDelete != null) {
                    Button(onClick = onDelete) { Text("Poista") }
                }
                Button(onClick = onDismiss) { Text("Peruuta") }
            }
        }
    )
}