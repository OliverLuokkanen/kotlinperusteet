package com.example.kotlinperusteet

fun addTask(list: List<Task>, task: Task): List<Task> =
    list + task

fun toggleDone(list: List<Task>, id: Int): List<Task> =
    list.map { t -> if (t.id == id) t.copy(done = !t.done) else t }

fun filterByDone(list: List<Task>, done: Boolean): List<Task> =
    list.filter { it.done == done }

fun sortByDueDate(list: List<Task>): List<Task> =
    list.sortedBy { it.dueDate }

fun deleteTask(list: List<Task>, id: Int): List<Task> =
    list.filterNot { it.id == id }

// UUSI: korvaa listasta task, jolla sama id
fun updateTask(list: List<Task>, updated: Task): List<Task> =
    list.map { t -> if (t.id == updated.id) updated else t }