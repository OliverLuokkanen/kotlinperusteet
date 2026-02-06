package com.example.kotlinperusteet.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kotlinperusteet.model.Task
import com.example.kotlinperusteet.model.mockTasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TaskViewModel : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(mockTasks)
    val tasks: StateFlow<List<Task>> = _tasks

    fun addTask(task: Task) {
        _tasks.update { it + task }
    }

    fun toggleDone(id: Int) {
        _tasks.update { list ->
            list.map { t -> if (t.id == id) t.copy(done = !t.done) else t }
        }
    }

    fun removeTask(id: Int) {
        _tasks.update { list -> list.filter { it.id != id } }
    }

    fun updateTask(updated: Task) {
        _tasks.update { list ->
            list.map { t -> if (t.id == updated.id) updated else t }
        }
    }
}