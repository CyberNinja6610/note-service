package ru.netology

interface CRUDService<T> {
    fun getAll(): Collection<T>
    fun getById(id: Int): T?
    fun add(element: T): T
    fun edit(id: Int, element: T): T
    fun delete(id: Int): T
}
