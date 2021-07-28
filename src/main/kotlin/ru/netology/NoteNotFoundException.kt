package ru.netology

import java.lang.Exception

class NoteNotFoundException(id: Int) : Exception("no note with id $id")