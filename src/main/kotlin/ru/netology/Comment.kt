package ru.netology

data class Comment(
    val id: Int = 0,
    val noteId: Int = 0,
    val isDeleted: Boolean = false,
    val message: String,
)
