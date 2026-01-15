package com.example.kotlinperusteet

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Int,
    val dueDate: String, // "YYYY-MM-DD"
    val done: Boolean
)

val mockTasks: List<Task> = listOf(
    Task(1, "Osta maitoa", "Käy kaupassa", 3, "2026-01-16", false),
    Task(2, "Palauta kirja", "Kirjastoon ennen eräpäivää", 4, "2026-01-18", true),
    Task(3, "Treeni", "Jalkatreeni salilla", 2, "2026-01-17", false),
    Task(4, "Koodaa", "Toteuta Kotlin-funktiot", 5, "2026-01-15", false),
    Task(5, "Soita mummolle", "Kuulumiset", 1, "2026-01-19", true),
    Task(6, "Siivoa", "Keittiö ja olohuone", 2, "2026-01-20", false),
    Task(7, "Maksa lasku", "Sähkölasku", 5, "2026-01-16", true),
    Task(8, "Varaa aika", "Hammaslääkäri", 4, "2026-01-22", false)
)