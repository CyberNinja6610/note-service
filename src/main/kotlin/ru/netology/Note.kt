package ru.netology

data class Note(
    val id: Int = 0,
    val isDeleted: Boolean = false,
    val title: String,
    val text: String,
)
