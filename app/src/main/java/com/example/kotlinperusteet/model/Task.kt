package com.example.kotlinperusteet.model

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Int,
    val dueDate: String,
    val done: Boolean
)

val mockTasks: List<Task> = listOf(
    Task(1, "Osta maitoa", "Käy kaupassa", 3, "2026-01-16", false),
    Task(2, "Palauta kirja", "Kirjastoon ennen eräpäivää", 4, "2026-01-18", true),
    Task(3, "Treeni", "Jalkatreeni salilla", 2, "2026-01-17", false),
    Task(4, "Siivoa", "Keittiö ja olohuone", 2, "2026-01-20", false)
)