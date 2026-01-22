package com.example.kotlinperusteet

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {
    var tasks = mutableStateOf<List<Task>>(mockTasks)
        private set

    fun addTask(task: Task) {
        tasks.value = addTask(tasks.value, task)
    }

    fun toggleDone(id: Int) {
        tasks.value = toggleDone(tasks.value, id)
    }

    fun removeTask(id: Int) {
        tasks.value = removeTask(tasks.value, id)
    }

    fun filterByDone(done: Boolean) {
        tasks.value = filterByDone(mockTasks, done)
    }

    fun sortByDueDate() {
        tasks.value = sortByDueDate(tasks.value)
    }

    fun resetTasks() {
        tasks.value = mockTasks
    }
}