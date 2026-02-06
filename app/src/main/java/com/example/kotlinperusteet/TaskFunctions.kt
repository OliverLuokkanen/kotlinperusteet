package com.example.kotlinperusteet

import com.example.kotlinperusteet.model.Task

fun addTask(list: List<Task>, task: Task): List<Task> {
    return list + task
}

fun toggleDone(list: List<Task>, id: Int): List<Task> {
    return list.map { t ->
        if (t.id == id) t.copy(done = !t. done) else t
    }
}

fun removeTask(list:  List<Task>, id: Int): List<Task> {
    return list.filter { it.id != id }
}

fun filterByDone(list:  List<Task>, done: Boolean): List<Task> {
    return list.filter { it.done == done }
}

fun sortByDueDate(list: List<Task>): List<Task> {
    return list.sortedBy { it.dueDate }
}