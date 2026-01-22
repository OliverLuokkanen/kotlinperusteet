package com.example.kotlinperusteet

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Int,
    val dueDate: String,
    val done: Boolean
)

val mockTasks: List<Task> = listOf(
    Task(id = 1, title = "Osta maitoa", description = "Käy kaupassa", priority = 3, dueDate = "2026-01-16", done = false),
    Task(id = 2, title = "Palauta kirja", description = "Kirjastoon ennen eräpäivää", priority = 4, dueDate = "2026-01-18", done = true),
    Task(id = 3, title = "Treeni", description = "Jalkatreeni salilla", priority = 2, dueDate = "2026-01-17", done = false),
    Task(id = 4, title = "Koodaa", description = "Toteuta Kotlin-funktiot", priority = 5, dueDate = "2026-01-15", done = false),
    Task(id = 5, title = "Soita mummolle", description = "Kuulumiset", priority = 1, dueDate = "2026-01-19", done = true),
    Task(id = 6, title = "Siivoa", description = "Keittiö ja olohuone", priority = 2, dueDate = "2026-01-20", done = false),
    Task(id = 7, title = "Maksa lasku", description = "Sähkölasku", priority = 5, dueDate = "2026-01-16", done = true),
    Task(id = 8, title = "Varaa aika", description = "Hammaslääkäri", priority = 4, dueDate = "2026-01-22", done = false)
)