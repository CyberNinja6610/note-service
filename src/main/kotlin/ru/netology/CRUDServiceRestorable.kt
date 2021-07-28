package ru.netology

interface CRUDServiceRestorable<T> : CRUDService<T> {
    fun restore(id: Int): T;
}